# pact-consumer

## How to set up pact broker locally

`git clone https://github.com/DiUS/pact_broker-docker.git`

`cd pact_broker-docker/`

`brew update`

`brew install rbenv`

`echo ‘eval “$(rbenv init -)“’ >> ~/.bash_profile`

`. ~/.bash_profile`

`rbenv install 2.5.1`

`which ruby`

`rbenv global 2.5.1`

`rbenv rehash`

`ruby -v`

`gem install bundler`

`which bundle`

`bundle install`

Attempting to run

`DISPOSABLE_PSQL=true script/test.sh`

This reveals that disposable PSQL is only supported under Linux. Instead, we have to start PostgreSQL in its own Docker container:

`sudo mkdir -p /private/var/lib/postgresql/data`

`sudo chmod 777 /private/var/lib/postgresql/data`

`docker run --name pactbroker-db -e POSTGRES_PASSWORD=AdminOfPactBrokerDb -e POSTGRES_USER=admin -e PGDATA=/var/lib/postgresql/data/pgdata -v /private/var/lib/postgresql/data:/var/lib/postgresql/data -d postgres`

Wait about 20 seconds

`docker run -it --link pactbroker-db:postgres --rm postgres sh -c ‘exec psql -h “$POSTGRES_PORT_5432_TCP_ADDR” -p “$POSTGRES_PORT_5432_TCP_PORT” -U admin’`

At this point, enter the admin password specified as POSTGRES_PASSWORD above to get a admin=# prompt.

`CREATE USER pactbrokeruser WITH PASSWORD ‘BrokerOfPacts’;`

`CREATE DATABASE pactbroker WITH OWNER pactbrokeruser;`

`GRANT ALL PRIVILEGES ON DATABASE pactbroker TO pactbrokeruser;`

`\q`

`docker run --name pactbroker --link pactbroker-db:postgres -e PACT_BROKER_DATABASE_USERNAME=pactbrokeruser -e PACT_BROKER_DATABASE_PASSWORD=BrokerOfPacts -e PACT_BROKER_DATABASE_HOST=postgres -e PACT_BROKER_DATABASE_NAME=pactbroker -d -p 80:80 dius/pact-broker`

Now connect a browser to localhost:80. There are no pacts shown.
