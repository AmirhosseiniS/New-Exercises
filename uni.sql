CREATE TABLE faculties
(
  name VARCHAR(40) NOT NULL,
  id INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE courses
(
  id INT NOT NULL,
  title VARCHAR(40) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE students
(
  id INT NOT NULL,
  first_name VARCHAR(40) NOT NULL,
  last_name VARCHAR(40) NOT NULL,
  faculty_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (faculty_id) REFERENCES faculties(id),
  FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE teachers
(
  id INT NOT NULL,
  first_name VARCHAR(40) NOT NULL,
  last_name VARCHAR(40) NOT NULL,
  course_id INT NOT NULL,
  faculty_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (faculty_id) REFERENCES faculties(id)
);


