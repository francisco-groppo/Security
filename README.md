## Security

### Getting Started

To run this project, Redis and Postgres are needed. The easiest way to get started is to have Docker installed, then run the following commands on a terminal:

1. docker run -d -p 6379:6379 --name myredis --network redisnet redis

1. docker run -itd -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v /data:/var/lib/postgresql/data --name postgresql postgres
