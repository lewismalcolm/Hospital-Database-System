CREATE TABLE PATIENT (
    patient_id VARCHAR2(9) PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    middle_initial VARCHAR2(1),
    last_name VARCHAR2(50) NOT NULL,
    ssn VARCHAR2(11) UNIQUE,
    current_address VARCHAR2(100),
    current_phone VARCHAR2(12),
    permanent_address VARCHAR2(100),
    permanent_phone VARCHAR2(12),
    birthdate VARCHAR2(25),
    sex VARCHAR2(10),
    condition VARCHAR2(10),
    primary_doctor_id VARCHAR2(9),
    secondary_doctor_id VARCHAR2(9)
);

CREATE TABLE DEPARTMENT (
    department_code VARCHAR2(4) PRIMARY KEY,
    department_name VARCHAR2(50) UNIQUE,
    office_number VARCHAR2(4),
    office_phone VARCHAR2(12),
    department_head_id VARCHAR2(9)
);

CREATE TABLE PROCEDURES (
    procedure_number VARCHAR2(7) PRIMARY KEY,
    procedure_name VARCHAR2(50),
    description VARCHAR2(200),
    duration VARCHAR2(25),
    offering_department_code VARCHAR2(4),
    FOREIGN KEY (offering_department_code) REFERENCES DEPARTMENT (department_code)
);

CREATE TABLE DOCTORS (
    doctor_id VARCHAR2(9) PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    middle_initial VARCHAR2(1),
    last_name VARCHAR2(50) NOT NULL,
    ssn VARCHAR2(11) UNIQUE,
    address VARCHAR2(100),
    phone VARCHAR2(12),
    birthdate VARCHAR2(25),
    contact_number VARCHAR2(12),
    department_code VARCHAR2(4),
    FOREIGN KEY (department_code) REFERENCES DEPARTMENT (department_code)
);

CREATE TABLE INTERACTION_RECORD (
    interaction_id INT PRIMARY KEY,
    patient_id VARCHAR2(9),
    int_date VARCHAR2(25),
    int_time VARCHAR2(10),
    int_description VARCHAR2(200),
    FOREIGN KEY (patient_id) REFERENCES PATIENT (patient_id)
);

CREATE TABLE PROCEDURES_DONE (
    procedure_number VARCHAR2(7),
    patient_id VARCHAR2(9),
    doctor_id VARCHAR2(9),
    pd_date VARCHAR2(25),
    pd_time VARCHAR2(25),
    notes VARCHAR2(200),
    FOREIGN KEY (procedure_number) REFERENCES PROCEDURES (procedure_number),
    FOREIGN KEY (patient_id) REFERENCES PATIENT (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES DOCTORS (doctor_id)
);

CREATE TABLE MEDICATION (
    medication_name VARCHAR2(50) PRIMARY KEY,
    manufacturer VARCHAR2(50),
    description VARCHAR2(200)
);

CREATE TABLE PRESCRIPTIONS (
    medication_name VARCHAR2(50),
    patient_id VARCHAR2(9),
    doctor_id VARCHAR2(9),
    date_prescribed VARCHAR2(25),
    FOREIGN KEY (medication_name) REFERENCES MEDICATION (medication_name),
    FOREIGN KEY (patient_id) REFERENCES PATIENT (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES DOCTORS (doctor_id)
);
