INSERT INTO roles(role_name)
VALUES ('admin'),
       ('inspector'),
       ('company');

INSERT INTO users (mail, phone, password)
VALUES ('admin292009@gmail.com', '0220024720', 'qweqwe'),
       ('inspector292009@gmail.com', '0220024745', 'qweqwe'),
       ('technokg@gmail.com', '0704543245', 'qweqwe');

INSERT INTO users(mail, phone, password)
VALUES ('individual@gmail.com', '59430239124', 'qweqwe');

INSERT INTO m2m_users_roles(user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO companies (name, tin, address)
VALUES ('technoKg', '02503202310286', 'Улица Горького 2/10');

INSERT INTO individuals (user_id, full_name, passport_series, birth_date, address, tin)
VALUES ((SELECT users.id FROM users where mail = 'individual@gmail.com'), 'Altynbekov Nursyltan', '122435454664564', '2009-02-26','Ахумбаева 96' , '01607200810123');

UPDATE users set company_id = (select id from companies where name = 'technoKg') where mail = 'technokg@gmail.com';

SELECT * FROM users;

INSERT INTO file_storage(original_file_name, mime_type, size, path, uploaded_by)
VALUES ('certificate.pdf', 'pdf', 250, 'minio/documents', 3);

INSERT INTO company_documents(company_id, document_type, file_id)
VALUES (1, 'COMPANY_REG_CERTIFICATE', 1);

SELECT * FROM company_documents;
SELECT * FROM companies;

UPDATE companies SET status = 'APPROVED' where name = 'technoKg';
UPDATE companies SET verified_by = (SELECT id FROM users where mail = 'inspector292009@gmail.com') where name = 'technoKg';
UPDATE companies SET verified_at = now() where name = 'technoKg';

UPDATE company_documents SET status = 'APPROVED' where id = 1;
UPDATE company_documents SET verified_by = (SELECT id FROM users where mail = 'inspector292009@gmail.com') where id = 1;
UPDATE company_documents SET verified_at = now() where id = 1;

UPDATE individuals SET status = 'APPROVED' where id = 1;
UPDATE individuals SET verified_by = (SELECT id FROM users where mail = 'inspector292009@gmail.com') where id = 1;
UPDATE individuals SET verified_at = now() where id = 1;

SELECT * FROM individuals;

INSERT INTO declarations(company_id, individual_id, type)
VALUES (1, null, 'EXPORT'),
       (1, null, 'IMPORT');

SELECT * FROM declarations;

UPDATE declarations set reviewed_by = (select id from users where mail = 'inspector292009@gmail.com') where company_id = 1;
UPDATE declarations set submitted_at = '2025-11-22 12:40:39.397172 +00:00' where company_id = 1;

INSERT INTO tnved_codes (code, description, default_customs_duty_rate, default_excise_rate, default_nds_rate)
VALUES ('2342534543534542', 'Код для картошки', 0.12, 0.10, 0.15);

INSERT INTO declaration_products (declaration_id, name, tnved_code, quantity, weight, price_per_unit, default_nds_rate, country_of_origin)
VALUES (1, 'Картошка свежая', (select id from tnved_codes where code = '2342534543534542'), 25, 55.5, 1300, (SELECT default_nds_rate from tnved_codes where code = '2342534543534542'), 'CHINA');

INSERT INTO file_storage(original_file_name, mime_type, size, path, uploaded_by)
VALUES ('ProductCertificateOrigin.pdf', 'pdf', 150, 'MinIo/backet/fileStorage', (SELECT id FROM users where mail = 'technokg@gmail.com'));

INSERT INTO declaration_documents(declaration_id, product_id, type, file_id)
VALUES (1, 1, 'PRODUCT_CERTIFICATE_ORIGIN', 2);

UPDATE declaration_documents SET verified_by = (SELECT id FROM users where mail = 'inspector292009@gmail.com') where id = 1;
UPDATE declaration_documents SET verified_at = now() where id = 1;
UPDATE declaration_documents SET status = 'APPROVED' where id = 1;
UPDATE declaration_products SET status = 'APPROVED' where id = 1;
UPDATE declaration_products SET verified_by = (select id from users where mail = 'inspector292009@gmail.com') where id = 1;
UPDATE declaration_products SET verified_at = now() where id = 1;


INSERT INTO payment_invoices (company_id, declaration_id, total_invoice_nds, total_invoice_customs_duty, total_invoice_excise, invoice_total)
VALUES (
           (SELECT company_id FROM declarations where company_id = (SELECT id FROM companies where name = 'technoKg' LIMIT 1) LIMIT 1),
        (SELECT id FROM declarations where id = 1),
                                         (SELECT SUM(price_per_unit * default_nds_rate) FROM declaration_products where name = 'Картошка свежая'),
                                                                                                                      (SELECT SUM(price_per_unit * default_customs_duty_rate) FROM declaration_products where name = 'Картошка свежая'),
                                                                                                                                                                                                            (SELECT SUM(price_per_unit * default_excise_rate) FROM declaration_products where name = 'Картошка свежая'),
                                                                                                                                                                                                                                                                                            (SELECT SUM(price_per_unit * default_nds_rate + price_per_unit * default_excise_rate + price_per_unit * default_customs_duty_rate) FROM declaration_products where name = 'Картошка свежая')
    );

INSERT INTO invoice_descriptions (invoice_id, description, amount, source_product_id)
VALUES (
           7,
           'НДС за ' || (SELECT name FROM declaration_products where id = 1) || (SELECT default_nds_rate FROM declaration_products where id = 1),
           (SELECT SUM( price_per_unit * default_nds_rate) FROM declaration_products where id = 1),
           1
       ),
       (
           7,
           'ЭКЦИЗ за ' || (SELECT name FROM declaration_products where id = 1) || (SELECT default_excise_rate FROM declaration_products where id = 1),
           (SELECT SUM( price_per_unit * default_excise_rate) FROM declaration_products where id = 1),
           1
       ),
       (
           7,
           'ПОШЛИНА за ' || (SELECT name FROM declaration_products where id = 1) || (SELECT default_customs_duty_rate FROM declaration_products where id = 1),
           (SELECT SUM( price_per_unit * default_customs_duty_rate) FROM declaration_products where id = 1),
           1
       );


SELECT * FROM tnved_codes;
SELECT * FROM declaration_products;
SELECT * FROM declaration_documents;
SELECT * FROM file_storage;
SELECT * FROM payment_invoices;
SELECT * FROM invoice_descriptions;

SELECT name, pinv.status, total_invoice_nds, total_invoice_excise, total_invoice_customs_duty, invoice_total, date_created, date_to_pay, description, amount FROM invoice_descriptions invd
                                                                                                                                                                      join payment_invoices pinv on invd.invoice_id = pinv.id join companies on pinv.company_id = companies.id;




INSERT INTO reported_caches (company_id, period_start, period_end, total_import, total_export, total_taxes_paid, total_rejected)
VALUES (
           (SELECT id FROM companies where name = 'technoKg'),
           (SELECT verified_at FROM companies where name = 'technoKg'),
           now()
       );

