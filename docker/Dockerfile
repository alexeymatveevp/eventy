FROM elasticsearch:alpine
COPY ./elasticsearch.yml /usr/share/elasticsearch/config/elasticsearch.yml
RUN chmod 664 /usr/share/elasticsearch/config/elasticsearch.yml
RUN chown elasticsearch:elasticsearch /usr/share/elasticsearch/config/elasticsearch.yml
CMD ["elasticsearch"]
EXPOSE 9200 9300