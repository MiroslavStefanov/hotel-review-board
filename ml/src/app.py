from kafka import KafkaProducer
from kafka import KafkaConsumer
from json import dumps
from json import loads
from time import sleep
import random


def handle_messages(producer, consumer):
    for message in consumer:
        message = message.value
        print(message)
        message['score'] = random.randint(1, 6)
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
    producer = recreate_on_fail(lambda: KafkaProducer(bootstrap_servers=['kafka-2:9092'],
                                                      value_serializer=lambda x:
                                                      dumps(x).encode('utf-8')),
                                "Error connecting kafka producer. Retrying...")
    consumer = recreate_on_fail(lambda: KafkaConsumer(
        'review',
        bootstrap_servers=['kafka-2:9092'],
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='model',
        value_deserializer=lambda v: loads(v.decode('utf-8'))), "Error connecting kafka consumer. Retrying...")

    while True:
        handle_messages(producer, consumer)
        sleep(5)


if __name__ == "__main__":
    main()
