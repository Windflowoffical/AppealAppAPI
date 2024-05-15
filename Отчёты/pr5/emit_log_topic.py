# Модуль для работы с RabbitMQ
import pika

# Модуль sys
import sys

# Установка соединения с сервером RabbitMQ, который преждевременно поднят через docker container на локальной машине
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672))

# Создаем канал связи через установленное соединение с RabbitMQ
channel = connection.channel()

# Объявляем обменник (exchange) с именем 'topic_logs' и типом 'topic'
channel.exchange_declare(exchange='topic_logs', exchange_type='topic')

# Определяем ключ маршрутизации для сообщения из аргументов командной строки или используем 'anonymous.info' по умолчанию
routing_key = sys.argv[1] if len(sys.argv) > 2 else 'anonymous.info'

# Сообщение для отправки, если в аргументах командной строки присутствуют сообщения, то они объединяются в одну строку, иначе отправляется 'Hello World!'
message = ' '.join(sys.argv[2:]) or 'Hello World!'

# Публикуем сообщение в обменник с заданным ключом маршрутизации
channel.basic_publish(
    exchange='topic_logs', routing_key=routing_key, body=message)

# Выводим информацию о том, что сообщение отправлено
print(f" [x] Sent {routing_key}:{message}")

# Закрываем соединение
connection.close()