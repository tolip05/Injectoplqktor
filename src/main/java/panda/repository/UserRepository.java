package panda.repository;

import panda.domein.entities.User;

public interface UserRepository extends GenericRepository<User,String> {
     User findByUserName(String username);
}
