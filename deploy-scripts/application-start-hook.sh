#!/bin/bash
echo "=== 배포 스크립트 시작 ==="

# 1. 기존에 실행 중인 백엔드(Java) 프로세스가 있다면 종료 (8080 포트 기준)
CURRENT_PID=$(sudo lsof -t -i:8080)
if [ -z "$CURRENT_PID" ]; then
    echo "현재 실행 중인 백엔드 프로세스가 없습니다."
else
    echo "기존 백엔드 프로세스 종료 중 (PID: $CURRENT_PID)..."
    sudo kill -15 $CURRENT_PID
    sleep 5
fi

# 2. appspec.yml에서 지정한 백엔드 고정 배포 위치로 이동
cd /home/ec2-user/backend

# 3. 백엔드 서버 실행 (로그를 해당 폴더의 backend.log에 영구 저장)
echo "새로운 백엔드 스프링부트 서버 실행..."
sudo nohup java -jar *.jar > /home/ec2-user/backend/backend.log 2>&1 &

# 4. Nginx 재시작 (Amazon Linux 환경 대응)
echo "Nginx 재시작..."
sudo systemctl restart nginx || sudo service nginx restart

echo "=== 배포 스크립트 완료 ==="