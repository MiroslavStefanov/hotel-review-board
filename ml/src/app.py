from kafka import KafkaProducer
from kafka import KafkaConsumer
from json import dumps
from json import loads
from time import sleep
import pickle


def handle_messages(producer, consumer, model, vectorizer):
    for message in consumer:
        message = message.value
        print(message)

        pred = model.predict(vectorizer.transform([message['content']]))[0]
        print("predicted class:", pred)

        message['score'] = pred
        producer.send('review-score', value=message)
        sleep(5)


def recreate_on_fail(factory, message):
    while True:
        try:
            return factory()
        except:
            if message:
                print(message)
            sleep(2)


def main():
    print("Starting up model app")

    producer = recreate_on_fail(lambda: KafkaProducer(bootstrap_servers=['kafka-1:9092'],
                                                      value_serializer=lambda x:
                                                      dumps(x).encode('utf-8')),
                                "Error connecting kafka producer. Retrying...")
    consumer = recreate_on_fail(lambda: KafkaConsumer(
        'review',
        bootstrap_servers=['kafka-1:9092'],
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='model',
        value_deserializer=lambda v: loads(v.decode('utf-8'))), "Error connecting kafka consumer. Retrying...")

    model_path = './resources/model.pickle'
    vectorizer_path = './resources/vectorizer.pickle'

    print("Start loading model")

    vectorizer = pickle.load(open(vectorizer_path, 'rb'))
    model = pickle.load(open(model_path, 'rb'))

    print("Model loaded")

    while True:
        handle_messages(producer, consumer, model, vectorizer)
        sleep(5)


if __name__ == "__main__":
    main()
