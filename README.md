# Matko — Yapay Zeka Destekli Matematik Yardımcısı

<div align="center">
  <img src="https://github.com/user-attachments/assets/91e9f427-fd4d-4ffa-a8d2-37eebdc064e0" alt="Matko Icon" width="200" height="200">
</div>

<div align="center">
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin Badge">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white" alt="Android Studio Badge">
  <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white" alt="Firebase Badge">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white" alt="Jetpack Compose Badge">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="MIT License Badge">
</div>

---

## Proje Hakkında

**Matko**, Türkiye’de sınav sürecinde matematikte zorluk yaşayan öğrencilere ve yetişkinlere yönelik geliştirilmiş, yapay zeka destekli bir mobil uygulamadır. Kullanıcıların motivasyonlarını artırmayı ve matematik becerilerini geliştirmeyi amaçlar.

Matko, sınav türü, derece ve matematik seviyesine göre kişiselleştirilmiş destek sunarak öğrenme deneyimini optimize eder.

---

## Temel Özellikler

- **Kullanıcı Kayıt ve Kimlik Doğrulama:** Firebase Authentication ile güvenli kayıt ve giriş.
- **Sınav Seçimi:** TYT, AYT, ALES, DGS, MSÜ, KPSS-A ve KPSS-B gibi yaygın sınavlar arasından seçim.
- **Derece Seçimi (KPSS-B için):** Ortaöğretim, Ön Lisans ve Lisans derecelerine göre özelleştirme.
- **Matematik Seviyesi Belirleme:** Temel, Orta ve İleri seviye seçenekleri ile kişiye özel öğrenme yolu.
- **Yapay Zeka Destekli Materyal ve Soru Çözümü:** Kullanıcıların sorularına ve ihtiyaçlarına uygun yapay zeka etkileşimi.
- **Modern UI:** Jetpack Compose ile sade, hızlı ve kullanıcı dostu arayüz.
- **Backend Hizmetleri:** Firebase Firestore ve Authentication ile verilerin güvenli yönetimi.
- **Dependency Injection:** Hilt ile temiz ve test edilebilir kod yapısı.
- **API İletişimi:** Retrofit ile REST API entegrasyonu.

---

## Teknolojiler

| Teknoloji     | Açıklama                                 |
|---------------|-----------------------------------------|
| Kotlin        | Modern ve güvenli Android programlama dili |
| Jetpack Compose | Android için modern UI toolkit           |
| Firebase      | Gerçek zamanlı veritabanı ve kimlik doğrulama |
| Hilt          | Bağımlılık Enjeksiyonu                  |
| Retrofit      | API istemcisi                           |
| ViewModel     | UI verilerinin yönetimi                  |

---
## Ekran Görüntüleri
![IMG-20250811-WA0001](https://github.com/user-attachments/assets/6451c78e-03b9-4105-ba17-eaaea11e11ba)
![IMG-20250811-WA0002](https://github.com/user-attachments/assets/79aab644-60ff-4749-94c3-a4d5a57160db)
![IMG-20250811-WA0003](https://github.com/user-attachments/assets/906834dc-78ee-44d6-b797-afdfa9a16034)
![IMG-20250811-WA0004](https://github.com/user-attachments/assets/437baa32-dfba-470c-8ab2-3fb69c5e4504)
![IMG-20250811-WA0005](https://github.com/user-attachments/assets/3687005d-1c3f-4c8b-ba3e-9139127e301a)
![IMG-20250811-WA0006](https://github.com/user-attachments/assets/b5a111dd-1c21-4f80-9563-fe3da8c37baf)
![IMG-20250811-WA0007](https://github.com/user-attachments/assets/ed9f5181-15dc-4419-89a5-e606bb9ca858)


https://github.com/user-attachments/assets/d280ef90-815e-4150-9ca9-b3923dac9d56



---
## Kullanım Akışı

1. **Kullanıcı Kayıt ve Giriş:** Uygulamaya kayıt olunur ve güvenli şekilde giriş yapılır.
2. **Sınav Türü Seçimi:** Kullanıcı hedeflediği sınavı seçer (ör. TYT, AYT, KPSS-B).
3. **Derece Seçimi (KPSS-B için):** KPSS-B seçilirse, eğitim derecesi belirlenir.
4. **Matematik Seviyesi:** Kullanıcının mevcut matematik seviyesi (Temel, Orta, İleri) seçilir.
5. **Yapay Zeka Etkileşimi:** Kullanıcıya seçilen parametrelere göre yapay zeka tabanlı matematik desteği sunulur.

---

## Kurulum ve Çalıştırma

1. Projeyi klonlayın:
   ```bash
   git clone https://github.com/kullaniciadi/matko.git
