# Kahve Backend

Bu proje Kahve uygulamasının backend servisidir. Spring Boot ve MongoDB kullanılarak geliştirilmiştir.

## Gereksinimler

- Docker
- Docker Compose

## Kurulum ve Çalıştırma

Projeyi Docker ile ayağa kaldırmak için proje dizininde terminali açın ve aşağıdaki komutu çalıştırın:

```bash
docker-compose up --build
```

Bu komut:
1. Uygulamayı Maven ile build eder.
2. Docker imajını oluşturur.
3. Konteyneri 8080 portunda başlatır.

Uygulama başladığında `http://localhost:8080` adresinden erişilebilir.

## Geliştirme

Lokal geliştirme için:
1. JDK 21 yüklü olmalıdır.
2. `mvn clean install` ile bağımlılıkları yükleyebilirsiniz.
3. `mvn spring-boot:run` ile uygulamayı başlatabilirsiniz.

