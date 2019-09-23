CREATE SCHEMA cin DEFAULT CHARACTER SET utf8 ;

use cin;

CREATE TABLE Equipamento (
  id_equipamento BIGINT NOT NULL AUTO_INCREMENT,
  tipo VARCHAR(100) NOT NULL,
  modelo VARCHAR(255) NOT NULL,
  mesano VARCHAR(20) NOT NULL,
  valor DECIMAL NOT NULL,
  foto LONGBLOB NOT NULL,
  PRIMARY KEY(id_equipamento)
);

INSERT INTO `cin.Equipamento` (`id_equipamento`,`tipo`,`modelo`,`mesano`,`valor`,`foto`) VALUES (3,'ELETRONICO','casa','1900-01',1111111111,?);
INSERT INTO `cin.Equipamento` (`id_equipamento`,`tipo`,`modelo`,`mesano`,`valor`,`foto`) VALUES (4,'AUTOMOTIVO','Fox Xtreme2019','2019-09',6000000000,?);

select * from cin.Equipamento;