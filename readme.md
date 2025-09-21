# 픽버스 백엔드

### EC2 빌드 script
git pull origin main
chmod +x wait-for-it.sh 
./gradlew clean build
docker-compose up -d --build