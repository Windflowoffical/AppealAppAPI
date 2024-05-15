# Модуль для работы с RabbitMQ
import pika
# Модуль sys
import sys

# Установка соединения с сервером RabbitMQ, который преждевременно поднят через docker container на локальной машине
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672))

# Создаем канал связи через установленное соединение с RabbitMQ
channel = connection.channel()

# Объявляем очередь с параметром durable=True, что означает, что очередь будет сохранять свое состояние даже после перезапуска брокера сообщений.
channel.queue_declare(queue='valikov_pr5', durable=True)

# Сообщение ли из командой строки, либо по-дефолту "Hello World!"
message = ' '.join(sys.argv[1:]) or "Hello World!"

# Публикация, с параметром Persistent, означает, что гарантирует сохранение сообщения даже в случае перезапуска брокера
# При этом не указываем обменник (exchange=''), так как используется прямое (direct) взаимодействие с очередью
channel.basic_publish(
    exchange='',
    routing_key='valikov_pr5',
    body=message,
    properties=pika.BasicProperties(
        delivery_mode=pika.DeliveryMode.Persistent
    ))

# Вывод сообщения
print(f" [x] Sent {message}")

# Закрываем соединение и идём пить чай.
connection.close()