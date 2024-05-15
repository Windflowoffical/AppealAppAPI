# Модуль для работы с RabbitMQ
import pika

# Модуль, который обеспечивает доступ к функциям работы со временем.
import time

# Установка соединения с сервером RabbitMQ, который преждевременно поднят через docker container на локальной машине
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672))

# Создаем канал связи через установленное соединение с RabbitMQ
channel = connection.channel()

# Объявляем очередь с параметром durable=True, что означает, что очередь будет сохранять свое состояние даже после перезапуска брокера сообщений.
channel.queue_declare(queue='valikov_pr5', durable=True)
# Уведомление о том, что начался приёма сообщений
print(' [*] Waiting for messages. To exit press CTRL+C')

# ch - канал связи, передаваться будет созданный нами выше
# method - информация о доставке сообщения
# body - тело сообщения
def callback(ch, method, properties, body):
    print(f" [x] Received {body.decode()}")
    # Задержка выполнения, пропорциональная количеству дефисов
    time.sleep(body.count(b'-'))
    print(" [x] Done")
    # Подтверждение успешной обработки сообщения, чтобы брокер мог удалить сообщение из очереди
    ch.basic_ack(delivery_tag=method.delivery_tag)

# Устанавливается настройка, по которой каждый потребитель может предварительно получить только одно сообщение из очереди.
channel.basic_qos(prefetch_count=1)

# Начинается прослушивание моей очереди с вызовом функции callback при получении каждого нового сообщения.
channel.basic_consume(queue='valikov_pr5', on_message_callback=callback)

# Начало прослушивания очереди и обработка сообщений (по сути, бесконечный цикл)
channel.start_consuming()