# Подключение нужных модулей, pika - для непосредственной работы с RabbitMQ
import pika, sys, os


def main():
    # Установка соединения с сервером RabbitMQ, который преждевременно поднят через docker container на локальной машине
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672))

    # Создаем канал связи через установленное соединение с RabbitMQ
    channel = connection.channel()

    # Создание или подключение к уже существующей очереди, в данном случае создание
    channel.queue_declare(queue='valikov_pr5', auto_delete=True)

    # Функция обратного вызова для приема сообщений из очереди
    def callback(ch, method, properties, body):
        print(f" [x] Received {body}")

    # Начинается прослушивание моей очереди с вызовом функции callback при получении каждого нового сообщения.
    channel.basic_consume(queue='valikov_pr5', on_message_callback=callback, auto_ack=True)

    # Вывод сообщения ожидания передачи сообщений
    print(' [*] Waiting for messages. To exit press CTRL+C')

    # Начало прослушивания очереди и обработка сообщений (по сути, бесконечный цикл)
    channel.start_consuming()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        # Обработка прерывания работы скрипта по нажатию CTRL+C
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)