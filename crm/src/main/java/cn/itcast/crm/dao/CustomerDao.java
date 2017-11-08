package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaId(String fixedAreaId);

	@Query("update Customer set fixedAreaId=null where fixedAreaId=? ")
	@Modifying
	void updateFixedAreaIdIsNull(String fixedAreaId);

	@Query("update Customer set fixedAreaId=? where id=? ")
	@Modifying
	void updateFixedAreaId(String fixedAreaId, Integer id);

	Customer findByTelephone(String telephone);

	@Query("update Customer set type=1 where telephone=? ")
	@Modifying
	void updateType(String telephone);

	Customer findByEmailAndPassword(String email, String password);

	Customer findByTelephoneAndPassword(String telephone, String password);

	List<Customer> findByAddress(String address);

	@Query(value="select t.*, t.rowid from T_CUSTOMER t where to_char(c_brithday,'yyyy/mm/dd') = to_char(sysdate,'yyyy/mm/dd')",nativeQuery=true)
	List<Customer> findByBirthDay();
}
