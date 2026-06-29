# FinanceSystem - K8s 기반 금융 서비스

1차 프로젝트

## 인프라 구조

```
[master]  ── K8s Control Plane (172.16.8.10)
[node1]   ── Spring Boot Pods (172.16.8.11)
[node2]   ── Spring Boot Pods + MySQL Pod (172.16.8.12)
[Storage] ── NFS 서버 (172.16.8.20)
```

## 전체 실행 순서

### 0. 사전 준비 (최초 1회)
```bash
# Metrics Server 설치 (HPA 작동을 위해 필수)
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

#ingress contoller 설치
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.12.0/deploy/static/provider/baremetal/deploy.yaml

# VM 환경이면 인증서 검증 우회 플래그 추가
kubectl -n kube-system edit deployment metrics-server
# args에 --kubelet-insecure-tls 추가 후 저장
```

### 1. K8s 리소스 배포
```bash
# 전체 YAML 한번에 적용 (순서대로 자동 처리됨)
kubectl apply -f k8s/

#(pvc를 변경할떄 해야함)
kubectl delete pv mysql-nfs-pv 

```


### 2. 배포 확인
```bash
# Spring Boot Pod 확인 (todo-app 네임스페이스)
kubectl get pods -o wide -n todo-app

# MySQL Pod 확인 (todo-db 네임스페이스)
kubectl get pods -o wide -n todo-db

# HPA 상태 확인
kubectl get hpa -n todo-app

# 전체 서비스 확인
kubectl get svc -n todo-app
kubectl get svc -n todo-db
```

### 3. 접속
```bash
# NodePort로 접속
http://<노드IP>:30090

# Ingress로 접속 (호스트 파일에 finance.local 등록 필요)
# /etc/hosts 또는 C:\Windows\System32\drivers\etc\hosts에 추가:
# <노드IP> finance.local
http://finance.local
```

---

## 부하 테스트 

```bash
# 터미널 1: Pod 실시간 감시 (어느 노드에 뜨는지 확인)
kubectl get pods -o wide -n todo-app -w

# 터미널 2: HPA 실시간 감시
kubectl get hpa -n todo-app -w

# 터미널 3: k6 부하 테스트 실행
k6 run tests/load/hpa-rampup.js
```

부하 투입 → CPU 50% 초과 → Pod 1개 → 최대 5개까지 자동 증가 확인
부하 종료 → Pod 수가 다시 1개로 감소 확인

---

## 데이터 정합성

- MySQL은 **node2에 고정** (`nodeName: node2`)
- **PV/PVC**로 NFS 서버(172.16.8.20)에 데이터 영구 저장
- Pod가 죽어도 `/nfs/data/mysql` 경로에 데이터 보존됨
- 확인 방법:
```bash
# MySQL Pod에 접속하여 데이터 확인
kubectl exec -it -n todo-db $(kubectl get pod -n todo-db -o name) -- mysql -uroot -p1234 testdb -e "SELECT COUNT(*) FROM transactions;"
```

---

## K8s YAML 파일 구성

| 파일 | 내용 |
|---|---|
| `01-namespace.yaml` | todo-app, todo-db 네임스페이스 생성 |
| `02-mysql.yaml` | Secret, PV/PVC, Service, Deployment (MySQL) |
| `03-springboot.yaml` | ConfigMap, Secret, Service(NodePort), Deployment (Spring Boot) |
| `04-hpa.yaml` | CPU 50% 기준 오토스케일링 (1~5 Pods) |
| `05-ingress.yaml` | Ingress (finance.local → springboot-service:8080) |
| `networkpolicy.yaml` | todo-app → todo-db 3306 포트만 허용 |

---

## 전체 삭제
```bash
kubectl delete -f k8s/
```
