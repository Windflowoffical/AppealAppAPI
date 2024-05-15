# Подключение модуля pika, который позволяет нам работать с RabbitMQ
import pika

# Установка соединения с сервером RabbitMQ, который преждевременно поднят через docker container на локальной машине
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672))

# Создаем канал связи через установленное соединение с RabbitMQ
channel = connection.channel()

# Создание или подключение к уже существующей очереди, в данном случае подключение
channel.queue_declare(queue='valikov_pr5', auto_delete=True)

# Отправляем сообщение с текстом 'Hello World!' в очередь valikov_pr5
# При этом не указываем обменник (exchange=''), так как используется прямое (direct) взаимодействие с очередью
channel.basic_publish(exchange='',
                      routing_key='valikov_pr5',
                      body='Hello World!')

# Выводим уведомление о том, что сообщение отправлено
print("[x] Sent 'Hello World!'")

# Закрываем соединение с RabbitMQ после отправки сообщения.
connection.close()