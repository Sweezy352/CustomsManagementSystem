# Таможенный Backend — Обновленная архитектура и требования

## 1. Основные функции системы

1. Регистрация пользователей (компаний и физических лиц)
2. Экспорт и импорт товаров в онлайн режиме
3. Учёт пошлин, НДС и акцизов
4. Прикрепление необходимых документов, подтверждающих качество товара
5. Дополнительные функции:
   - Личный кабинет компании
   - История деклараций
   - Система статусов деклараций
   - Проверка инспектором
   - Административная панель
   - Настройка ставок пошлин и НДС
   - Логи и аудит действий

---

## 2. Налоги и коды товаров

### ТН ВЭД
Код товара в международной классификации. Используется для:
- определения таможенных пошлин
- контроля разрешений и ограничений
- классификации по категориям

### НДС
Налог на добавленную стоимость. В Кыргызстане ставка стандартно 12%, но она должна быть настраиваемой через админ-панель системы.

### Акциз
Налог на отдельные категории товаров, например, алкоголь, топливо, сигареты, автомобили.

---

## 3. Управление налогами в системе

- Все ставки (НДС, пошлины, акциз) хранятся в базе данных.
- Администратор может изменять ставки и конфигурации.
- Таблицы: `TaxRate`, `CustomsDutyRate`, `ExciseRate`.
- Применение ставок при расчете налогов зависит от ТН ВЭД-кода и типа товара.

## 4. ву    Таможенная система — база данных и сущности

## 1. Пользователи

### User

* `id`
* `email` / `phone`
* `password`
* `role` (ADMIN / INSPECTOR / COMPANY_USER / INDIVIDUAL_USER)
* `created_at`
* `last_login`

> Администраторы и инспекторы не привязаны к компании. Пользователи-компаний связываются через таблицу `CompanyUser`.

### CompanyUser

* `id`
* `user_id` → User
* `company_id` → Company
* `role_in_company` (OWNER / MANAGER / ACCOUNTANT)

---

## 2. Компании

### Company

* `id`
* `name`
* `tin` (ИНН / регистрационный номер, `VARCHAR`, уникальное)
* `address` (VARCHAR или структурировано: country, city, street, building, apartment, postal_code)
* `status` (PENDING / VERIFIED / REJECTED)
* `created_at`
* `verified_by` → инспектор
* `verified_at`

### CompanyDocument

* `id`
* `company_id` → Company
* `document_type` (enum, см. ниже)
* `file_id` → FileStorage
* `uploaded_at`
* `verified` (boolean)
* `verified_by`
* `verified_at`

---

## 3. Физические лица

### Individual

* `id`
* `user_id` → User
* `full_name`
* `passport_series` / `passport_number`
* `birth_date`
* `address`
* `tin`
* `verified` (boolean)
* `verified_by` → инспектор
* `verified_at`

### IndividualDocument

* `id`
* `individual_id` → Individual
* `document_type` (паспорт, ID и др.)
* `file_id` → FileStorage
* `uploaded_at`

---

## 4. Декларации

### Declaration

* `id`
* `company_id` (nullable)
* `individual_id` (nullable)
* `type` (IMPORT / EXPORT)
* `status` (DRAFT / SUBMITTED / IN_REVIEW / APPROVED / REJECTED)
* `created_at`
* `submitted_at`
* `reviewed_by` → инспектор
* `approved_at`

### DeclarationProduct

* `id`
* `declaration_id` → Declaration
* `name`
* `tnved_code` → TnvedCode
* `quantity`
* `weight`
* `price_per_unit`
* `country_of_origin`

### DeclarationDocument

* `id`
* `declaration_id` → Declaration
* `product_id` (nullable) → DeclarationProduct
* `type` (enum)
* `file_id` → FileStorage
* `uploaded_at`

### DeclarationHistory

* `id`
* `declaration_id`
* `user_id`
* `action`
* `comment`
* `timestamp`

---

## 5. Справочники и риски

### TnvedCode

* `id`
* `code` (например: 8517120000)
* `description`
* `default_customs_duty_rate`
* `default_excise_rate`
* `default_vat_rate`

### RiskRule

* `id`
* `tnved_code` → TnvedCode
* `condition_json` (например, страна происхождения = Китай)
* `risk_level` (LOW / MEDIUM / HIGH)
* `description`
* `created_at`

### DeclarationRisk

* `id`
* `declaration_id` → Declaration
* `risk_level`
* `calculated_at`

---

## 6. Файлы и уведомления

### FileStorage

* `id`
* `original_name`
* `mime_type`
* `size`
* `path` (S3 / MinIO / локально)
* `uploaded_by` → User
* `uploaded_at`

### Notification

* `id`
* `user_id` → User
* `type` (EMAIL / SMS / SYSTEM)
* `message`
* `created_at`
* `read` (boolean)

---

## 7. Отчёты

### ReportCache

* `id`
* `company_id` → Company
* `period_start`
* `period_end`
* `total_imports`
* `total_exports`
* `total_taxes_paid`
* `total_decl_rejected`
* `generated_at`

---

## 8. ENUM для документов

### CompanyDocumentType

* COMPANY_REG_CERTIFICATE
* COMPANY_INN_CERTIFICATE
* COMPANY_CHARTER
* COMPANY_LICENSE_IMPORT
* COMPANY_LICENSE_EXPORT
* COMPANY_TAX_REGISTRATION
* COMPANY_VAT_CERTIFICATE
* COMPANY_POWER_OF_ATTORNEY

### DeclarationDocumentType

* PRODUCT_CERTIFICATE_ORIGIN
* PRODUCT_QUALITY_CERTIFICATE
* PRODUCT_TECH_SHEET
* PRODUCT_CONFORMITY_CERTIFICATE
* PRODUCT_MANUFACTURER_CERTIFICATE
* TRANSPORT_CMR
* TRANSPORT_BILL_OF_LADING
* TRANSPORT_AIRWAY_BILL
* TRANSPORT_INSURANCE
* FIN_INVOICE
* FIN_PROFORMA_INVOICE
* FIN_CONTRACT
* FIN_PACKING_LIST
* FIN_PAYMENT_RECEIPT
* CUSTOMS_DECLARATION
* CUSTOMS_EX1
* CUSTOMS_IMPORT_PERMISSION
* CUSTOMS_EXPORT_PERMISSION
* CUSTOMS_DUTY_CALCULATION

### IndividualDocumentType

* PERSON_PASSPORT
* PERSON_PHOTO_ID


## 5. Порядок регистрации

### Для компаний:
1. Пользователь создаёт аккаунт (email/телефон + пароль)
2. Заполняет данные компании через форму
3. После успешного создания компании: role = COMPANY, company_id присваивается пользователю
4. Если компания не создается в течение N дней — запись пользователя удаляется или деактивируется

### Для физических лиц (Individual):
- Паспортные данные обязательны
- Используется для разовых деклараций и частного импорта/экспорта

### Для ADMIN / INSPECTOR:
- company_id = null
- паспортные данные не нужны

---

## 6. Аналитика в системе

### Для компаний:
- Количество деклараций по статусу (DRAFT / SUBMITTED / APPROVED / REJECTED)
- Сумма начисленных пошлин, НДС, акцизов
- Разбивка по ТН ВЭД-кодам
- Статистика по документам и отклонениям
- Среднее время обработки декларации
- История операций пользователей

### Для инспекторов:
- Список деклараций для проверки с деталями
- Компании с наибольшим количеством ошибок
- Декларации с высоким уровнем риска
- Статистика по инспекторам
- Документы, вызывающие наибольшие замечания

### Для администраторов:
- Активность пользователей и компаний
- Общие показатели деклараций и налогов по всей системе
- Риски по ТН ВЭД-кодам и компаниям
- Логи всех действий

### Визуализация:
- Графики по месяцам, круговые диаграммы по налогам
- Таблицы с фильтрами по компаниям, инспекторам, ТН ВЭД-кодам
- Тепловые карты и Top-10 списки

---

## 7. Защита от «пустых» пользователей

- Временный статус PENDING для новых регистраций
- Пользователь не получает полноценный доступ, пока не создаст компанию
- Автоудаление или деактивация записи, если компания не создана в течение N дней
- Email подтверждение для верификации
- Можно внедрить модерацию через админ-панель

---

## 8. Итоговая логика

- **Company создаётся в системе только через пользователя**  
- **User привязан к компании через company_id** (nullable для ADMIN / INSPECTOR)  
- **Individual** для частных лиц с паспортными данными  
- **Аналитика** формируется по декларациям, налогам и рискам, визуализируется через дашборды
- **Налоги и пошлины** настраиваются админом, связаны с ТН ВЭД
- **Документы** прикрепляются к продуктам и декларациям, проверяются инспекторами

