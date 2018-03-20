
CREATE type adreca as
(
	poblacio text,
	provincia text,
	cp text,
	domicili text
);



CREATE TABLE persones (
		idpersona INT4 NOT NULL,
		nif TEXT,
		nom TEXT,
		adreca adreca,
		email TEXT,
		telefon TEXT
	);

CREATE TABLE proveidors (
		idpersona INT4 NOT NULL,
		nif TEXT,
		nom TEXT,
		adreca adreca,
		email TEXT,
		telefon TEXT,
		contacte TEXT
	);
CREATE TABLE productes (
		idproducte VARCHAR(15) NOT NULL,
		nom TEXT,
		preu NUMERIC(7 , 2),
		idproveidor INT4,
		stock INT4,
		fecha_inicio DATE,
		fecha_final DATE,
		tipo TEXT NOT NULL
	);

CREATE TABLE jocs (
		
		edat INT4
		
	)INHERITS (productes);

CREATE TABLE packs1 (
	
		porc_dto NUMERIC(3 , 0),
		jocs INT4[ ]
		
	)INHERITS (productes);;



CREATE TABLE clients (
		idpersona INT4 NOT NULL,
		nif TEXT,
		nom TEXT,
		adreca adreca,
		email TEXT,
		telefon TEXT,
		enviarpublicitat BOOL
	);


ALTER TABLE packs1 ADD CONSTRAINT packs1_pkey PRIMARY KEY (idproducte);

ALTER TABLE productes ADD CONSTRAINT productes_idproducte PRIMARY KEY (idproducte);

ALTER TABLE persones ADD CONSTRAINT persones_pkey PRIMARY KEY (idpersona);

ALTER TABLE jocs ADD CONSTRAINT jocs_pkey PRIMARY KEY (idproducte);

ALTER TABLE clients ADD CONSTRAINT clients_pkey PRIMARY KEY (idpersona);

ALTER TABLE proveidors ADD CONSTRAINT proveidors_pkey PRIMARY KEY (idpersona);

ALTER TABLE productes ADD CONSTRAINT productes_idproveidor_fkey FOREIGN KEY (idproveidor)
	REFERENCES proveidors (idpersona);


