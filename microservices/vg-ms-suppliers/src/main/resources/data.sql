INSERT INTO supplier (business_name, document_type, document_number, address, phone, email, image_url, status)
VALUES
    ('DISTRIBUIDORA FARMASUR EIRL', 'RUC', '20521386551',
     'CAL FIDEL OLIVOS ESCUDERO N°191 - SAN MIGUEL',
     '989575834', 'nelias@distribuidorafarmasur.com', NULL, TRUE),
    ('PROMER EIRL', 'RUC', '20523929691',
     'MZA. M1 LOTE 28 URB. SAN JUAN MASIAS - CALLAO',
     '979267441', 'promereirl@gmail.com', NULL, TRUE)
ON CONFLICT DO NOTHING;
