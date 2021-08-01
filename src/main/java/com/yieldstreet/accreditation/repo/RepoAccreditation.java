package com.yieldstreet.accreditation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yieldstreet.accreditation.model.Accreditation;


	
@Repository
public interface RepoAccreditation extends JpaRepository<Accreditation,Long>{

	Accreditation findByUserId(String userId);}
