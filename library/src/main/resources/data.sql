INSERT INTO library (address, city, library_uid, name) VALUES
    ('2-я Бауманская ул., д.5, стр.1', 'Москва', '83575e12-7ce0-48ee-9931-51919ff3c9ee', 'Библиотека имени 7 Непьющих');

INSERT INTO books (author, book_uid, condition, genre, name) VALUES
    ('Бьерн Страуструп', 'f7cdc58f-2caf-4b15-9727-f89dcc629b27', 'EXCELLENT', 'Научная фантастика', 'Краткий курс C++ в 7 томах');

INSERT INTO library_books (library_id, books_id) VALUES (1, 1);