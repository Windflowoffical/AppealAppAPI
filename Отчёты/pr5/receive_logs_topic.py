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

# Объявляем очередь, exclusive=True делает ее временной и уникальной
result = channel.queue_declare('', exclusive=True)
queue_name = result.method.queue

# Получаем ключи маршрутизации из аргументов командной строки
binding_keys = sys.argv[1:]
if not binding_keys:
    sys.stderr.write("Usage: %s [binding_key]...\n" % sys.argv[0])
    sys.exit(1)

# Привязываем очередь к обменнику с заданными ключами маршрутизации
for binding_key in binding_keys:
    channel.queue_bind(
        exchange='topic_logs', queue=queue_name, routing_key=binding_key)

# Выводим сообщение о готовности принимать сообщения
print(' [*] Waiting for logs. To exit press CTRL+C')

# Функция обратного вызова для обработки полученных сообщений
def callback(ch, method, properties, body):
    print(f" [x] {method.routing_key}:{body}")

# Устанавливаем функцию обратного вызова для обработки сообщений из очереди
channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

# Начало прослушивания очереди и обработка сообщений (по сути, бесконечный цикл)
channel.start_consuming()