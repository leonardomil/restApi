package com.yieldstreet.accreditation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yieldstreet.accreditation.model.Document;


	
@Repository
public interface RepoDocument extends JpaRepository<Document,Long>{}
