USE bookOLX;

INSERT INTO authors (authors_uuid, name, surname)
values
    ("d41f41f6-c469-495d-9fd1-4b4ddd37957e", "Rowling", "Joanne K"),
    ("635c4c57-abc0-476e-a941-4265cf164f73", "Tolkien", "John Ronald Reuel"),
    ("9f017f2a-4330-4a8c-9b3e-311bf638ddf1", "Kahneman", "Daniel"),
    ("d713c2d0-baee-4383-9207-7851cc4651e3", "Mitchell", "Erika"),
    ("350c18f7-c1b5-4d8b-9f7c-ab4fa5fda011", "Tchaikovsky", "Adrian")
;


INSERT INTO books (isbn, title, description, quantity)
values
    ('0747532699', "Harry Potter and the Philosopher's Stone", "Harry Potter and the Philosopher's Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling's debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. Harry makes close friends and a few enemies during his first year at the school, and with the help of his friends, he faces an attempted comeback by the dark wizard Lord Voldemort, who killed Harry's parents, but failed to kill Harry when he was just 15 months old.", 1),
    ('0747538492', "Harry Potter and the Chamber of Secrets", "Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry's second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school's corridors warn that the \"Chamber of Secrets\" has been opened and that the \"heir of Slytherin\" would kill all pupils who do not come from all-magical families. These threats are found after attacks that leave residents of the school petrified. Throughout the year, Harry and his friends Ron and Hermione investigate the attacks.",1),
    ('0747581088', "Harry Potter and the Half-Blood Prince", "Harry Potter and the Half-Blood Prince is a fantasy novel written by British author J.K. Rowling and the sixth and penultimate novel in the Harry Potter series. Set during Harry Potter's sixth year at Hogwarts, the novel explores the past of the boy wizard's nemesis, Lord Voldemort, and Harry's preparations for the final battle against Voldemort alongside his headmaster and mentor Albus Dumbledore." ,1),
    ('9780007488353', "The Return of the King", "Tolkien conceived of The Lord of the Rings as a single work comprising six \"books\" plus extensive appendices. In 1953, he proposed titles for the six books to his publisher, Rayner Unwin; Book Five was to be The War of the Ring, while Book Six was to be The End of the Third Age.[2] These titles were eventually used in the (2000) Millennium edition. Rayner Unwin however split the work into three volumes, publishing the fifth and sixth books with the appendices into the final volume with the title The Return of the King. Tolkien felt the chosen title revealed too much of the story, and indicated that he preferred The War of the Ring as a title for the volume.[3]" ,2),
    ('9780521792608', "Heuristics and Biases: The Psychology of Intuitive Judgment", "Tells you how and why you judge ppl", 1 ),
    ('9781612130286', "Fifty Shades of Grey", "Fifty Shades of Grey is a 2011 erotic romance novel by British author E. L. James.[1] It became the first instalment in the Fifty Shades novel series that follows the deepening relationship between a college graduate, Anastasia Steele, and a young business magnate, Christian Grey. It is notable for its explicitly erotic scenes featuring elements of sexual practices involving BDSM (bondage/discipline, dominance/submission, and sadism/masochism). Originally self-published as an ebook and print-on-demand in June 2011, the publishing rights to the novel were acquired by Vintage Books in March 2012.",1),
    ('9780330511445', "Salute the Dark", "Creepy SciFi" , 0),
    ('9780230757004', "The Air War ", "Something something War, kitty ipsom atack", 1)
;

INSERT INTO roles(role_id, role_name)
values
    (1, "ADMIN"),
    (2, "USER")
;


INSERT INTO members (member_uuid, username, password ,points, email_address)
values
    ("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9", "DanVerde", "Verde", 0,"dani@gmail.com"),
    ("6eca21ce-861b-4dd7-975d-20a969e3183a", "RoboAlin", "Robo",0,"robot@gmail.com"),
    ("13177e99-14b5-43c5-a446-e0dc751c3153", "RozzzAlina", "Roz", 0,"rozza@gmail.com")
;



INSERT INTO book_market (book_market_uuid, user_uuid, book_isbn, book_state, for_sell, sell_price, for_rent, rent_price, book_status)
values
    ("42a48524-20fd-4708-9311-55bf1a247eaf", "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9", '0747532699', "ASNEW", false, 0, true, 50, "AVAILABLE"),
    ("495c9b8d-5a71-4215-abe0-71a46e79a02c", "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9", '0747538492', "ASNEW", true, 200, false, 0, "AVAILABLE"),
    ("1c821fb0-1024-4cd0-8f23-2d763fb2c13b", "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9", '0747581088', "USED", false, 0, true, 25, "AVAILABLE"),
    ("1ec3d489-9aa0-4cad-8ab3-0ce21a669ddb", "6eca21ce-861b-4dd7-975d-20a969e3183a", '9780007488353', "USED", true, 100, false, 9, "AVAILABLE" ),
    ("b28cfca2-0ff9-4868-b881-a5331383339c", "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9", '9780330511445', "PARTIAL_DAMAGED", false, 0, false, 0, "SOLD"),
    ("17354736-146c-4544-90a9-3d83700e7b59", "6eca21ce-861b-4dd7-975d-20a969e3183a", '9780007488353', "ORIGINALBOX", true, 230, false, 0, "AVAILABLE"),
    ("eb1ab8b2-a2a4-4057-bba2-2d2caf65ce47", "13177e99-14b5-43c5-a446-e0dc751c3153", '9781612130286', "PARTIAL_DAMAGED",true, 110, false, 0, "AVAILABLE"),
    ("102126b8-49a9-4eb3-bc4f-424d89a56f8b", "13177e99-14b5-43c5-a446-e0dc751c3153", '9780521792608', "ORIGINALBOX", true, 400, false, 0, "AVAILABLE"),
    ("103cfb11-e1be-4486-ac18-3d0722017da7", "13177e99-14b5-43c5-a446-e0dc751c3153", '9780230757004', "USED", false, 0 , false, 0, "RENTED"),
    ("a4876a1d-a05f-4ce1-ac2f-3a988eb7ded4", "13177e99-14b5-43c5-a446-e0dc751c3153", '9780230757004', "USED", true, 20 , true, 3, "AVAILABLE")
;
INSERT INTO email_templates values (default, 'AVAILABILITY', 'Book availability notification', 'Hey %s , your book entitled %s is available');
INSERT INTO email_templates values (default, 'EMAIL_CONFIRMATION', 'New user confirmation', 'Hey %s , please confirm your account by clicking %s .');
INSERT INTO email_templates values (default, 'TRADE', 'Book trade needs accept', "Hey %s , your book entitled %s has been requested to be exchanged for the %s's book named %s . If you agree, please click on <a href=\"%s\">Accept</a>. If you dont agree or you don't know anything about this trade, click on <a href=\"%s\">Refuse</a>");
INSERT INTO email_templates values (default, 'TRANSACTION_SUCCES', 'You just made a purchase/rent', "Hey %s , You just made a purchase/rent. Thank you for this.");
INSERT INTO notifications (id, market_book_uuid, email_template_id, sent, member_uuid) VALUES (1, '1c821fb0-1024-4cd0-8f23-2d763fb2c13b', 1, 1, 'ae677979-ffec-4a90-a3e5-a5d1d31c0ee9');
INSERT INTO notifications (id, market_book_uuid, email_template_id, sent, member_uuid) VALUES (2, 'a4876a1d-a05f-4ce1-ac2f-3a988eb7ded4', 1, 1, '6eca21ce-861b-4dd7-975d-20a969e3183a');

