import pika

connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672))
channel = connection.channel()

channel.queue_declare(queue='valikov_pr5', auto_delete=True)

channel.basic_publish(exchange='',
                      routing_key='valikov_pr5',
                      body='Hello World!')
print("[x] Sent 'Hello World!'")

connection.close()