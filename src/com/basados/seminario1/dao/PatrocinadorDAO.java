/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao;
import com.basados.seminario1.model.Patrocinador;

/**
 *
 *  Patrocinador DAO (data object access) hace consultas a la BD relacionadas con
 *  un patrocinador.
 * 
 *  La implementacion para la BD de la ugr (Oracle) se encuentra en 
 *  OraclePatrocinadorDAO
 * 
 */
public interface PatrocinadorDAO extends DAO<Patrocinador,String>{
}
