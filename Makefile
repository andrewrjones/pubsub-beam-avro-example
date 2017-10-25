TOPIC=projects/project-id/topics/customers
PUBSUB=pubsub:8042

RUNNER=direct-runner

up:
	docker-compose up

down:
	docker-compose down

topic:
	docker-compose exec pubsub curl -X PUT http://$(PUBSUB)/v1/$(TOPIC)

compile:
	docker-compose run beam mvn compile

producer:
	docker-compose run beam mvn compile exec:java -Dexec.mainClass=com.andrewjones.PubSubProducerExample -P$(RUNNER)

consumer:
	docker-compose run beam mvn compile exec:java -Dexec.mainClass=com.andrewjones.PubSubConsumerExample -P$(RUNNER)

clean: clean-docker clean-files

clean-docker:
	docker-compose rm -f

clean-files:
	rm wordcounts*
