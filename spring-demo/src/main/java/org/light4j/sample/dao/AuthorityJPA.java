package org.light4j.sample.dao;

import org.light4j.sample.bean.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityJPA extends CrudRepository<Authority, String> {

}
