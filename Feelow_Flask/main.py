from flask import Flask, request, jsonify
import os
import json

from transformers import AutoModelForSequenceClassification, AutoTokenizer
import torch

app = Flask(__name__)

from langchain_openai import ChatOpenAI
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler
from langchain.prompts import StringPromptTemplate
from langchain.chains import ConversationChain
from langchain.chains.conversation.memory import ConversationBufferMemory

api_key = os.getenv('OPENAI_API_KEY')
llm = ChatOpenAI(api_key=api_key, model="gpt-3.5-turbo", temperature=0.8, )

PROMPT_TEMPLATE = """
context: {context}
history: {history}
input: {input}

Generate an answer to an input that takes into account context and history.
answer:
"""


# Define the custom prompt template class
class CustomPromptTemplate(StringPromptTemplate):
    context: str
    template: str

    def format(self, **kwargs) -> str:
        kwargs['context'] = self.context
        return self.template.format(**kwargs)


# Create an instance of the custom prompt template
PROMPT = CustomPromptTemplate(
    input_variables=["history", "input"],
    template=PROMPT_TEMPLATE,
    context="""
    You're a secret friend who talks with middle and high school students.
    Your name is Feelow.
    When responding, use lots of appropriate emojis.
    At the end of your answer, always ask students.
    Please always answer in Korean.
    Always use semi-finished sentences like "어", "맞아", "좋아", "해", "이야", "봐".
    Always answer in two sentences or less, and only ask one question.
    """
)

# Define the conversation memory
memory = ConversationBufferMemory(
    ai_prefix="Secret Friend",
    human_prefix='Student'
)

global_history = ""

# 감정 분석
# 감정 분석 모델 불러오기
huggingface_api_token = os.getenv('HUGGINGFACEHUB_API_TOKEN')

# Load the model and tokenizer
senti_tokenizer = AutoTokenizer.from_pretrained("matthewburke/korean_sentiment")
senti_model = AutoModelForSequenceClassification.from_pretrained("matthewburke/korean_sentiment")


def senti_score_json(input):
    # Tokenize input
    inputs = senti_tokenizer(input, return_tensors="pt")

    # Forward pass, get logits
    outputs = senti_model(**inputs)
    logits = outputs.logits

    # Softmax to get probabilities
    probabilities = torch.nn.functional.softmax(logits, dim=-1)

    # Get the probabilities for both classes
    negative_score = probabilities[0][0].item()
    positive_score = probabilities[0][1].item()

    # Convert the predicted class to human-readable labels
    labels = ["negative", "positive"]
    sentiment_label = labels[torch.argmax(probabilities).item()]

    # Print raw output for debugging
    print("Raw Output Probabilities:", probabilities.tolist())

    senti_score = {"negative": negative_score, "positive": positive_score}

    senti_score_json = json.dumps(senti_score, ensure_ascii=False, indent=2)
    print(senti_score_json)

    return senti_score


# Define the Flask route
@app.route("/api/chat", methods=['POST'])
def chat_endpoint():
    try:
        global global_history

        input_text = request.json['input']

        # Format the prompt using the conversation template
        formatted_prompt = PROMPT.format(history=global_history, input=input_text)

        # Use the language model to predict the response
        response = llm.predict(text=formatted_prompt)

        global_history += f"\nStudent: {input_text}\nSecret Friend: {response}"

        # Use the sentiment analysis model to score the input
        senti_score = senti_score_json(input_text)

        return jsonify(
            {'input': input_text, 'response': response, 'history': global_history, 'senti_score': senti_score})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# Define a simple hello route for testing
@app.route("/")
def hello():
    return "Hello, World!"


# Run the Flask app
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)