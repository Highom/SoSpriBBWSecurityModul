DELETE FROM message;
INSERT INTO message (id, content, author, origin) VALUES
  (1, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:30:40'),
  (2, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:31:22'),
  (3, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Mac Afee', '2020-03-10 10:38:11'),
  (4, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Tony Stark', '2020-03-10 10:42:57');

/* encrypted password for id 1..4 is 1234* */
DELETE FROM member;
INSERT INTO member (id, prename, lastname, password, username, authority) VALUES
  (1, 'Albert', 'Einstein', '8722ed82de46a868353f23817eae292641123ed30080722eea7ad963df64227b00ef455799e4e5ad', 'albert.einstein', 'admin'),
  (2, 'Mac',  'Afee', '8722ed82de46a868353f23817eae292641123ed30080722eea7ad963df64227b00ef455799e4e5ad', 'mac.afee', 'member'),
  (3, 'Tony',  'Stark', '8722ed82de46a868353f23817eae292641123ed30080722eea7ad963df64227b00ef455799e4e5ad', 'toni.stark', 'supervisor'),
  (4, 'Wilhelm',  'Tell', '8722ed82de46a868353f23817eae292641123ed30080722eea7ad963df64227b00ef455799e4e5ad', 'wilhelm.tell', 'member'),
  (5, 'Yannick', 'Ruck', '8722ed82de46a868353f23817eae292641123ed30080722eea7ad963df64227b00ef455799e4e5ad', 'yannick.ruck', 'admin');
