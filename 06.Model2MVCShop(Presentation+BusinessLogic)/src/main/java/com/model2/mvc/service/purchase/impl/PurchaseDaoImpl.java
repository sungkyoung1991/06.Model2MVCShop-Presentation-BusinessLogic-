package com.model2.mvc.service.purchase.impl;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::" + getClass() + ".setSqlSession() call..");
		this.sqlSession = sqlSession;
	}

	public PurchaseDaoImpl() {
		System.out.println("::" + getClass() + "default Constructor Call");
	}

	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);

	}

	@Override
	public Purchase findPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectMap("PurchaseMapper.getTotalCount", search, buyerId); // 모룸
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.selectOne("PurchaseMapper.getTotalCount", purchase);

	}

	@Override
	public void updatePurchase(Purchase purchase) throws SQLException {
		// TODO Auto-generated method stub
		sqlSession.selectOne("PurchaseMapper.getTotalCount", purchase);
		
	}

}
