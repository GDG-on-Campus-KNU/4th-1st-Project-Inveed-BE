# ARM64 네이티브 이미지를 사용하도록 platform 옵션은 docker-compose.yml에서 지정할 예정입니다.
FROM docker.elastic.co/logstash/logstash:8.12.0

# 커스텀 파이프라인 설정 파일들을 컨테이너 내 /usr/share/logstash/pipeline/ 디렉토리로 복사
COPY pipeline/ /usr/share/logstash/pipeline/

RUN elasticsearch-plugin install analysis-nori