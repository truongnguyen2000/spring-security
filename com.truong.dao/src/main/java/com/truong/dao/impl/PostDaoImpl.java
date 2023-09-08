package com.truong.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.truong.dao.AbstractDao;
import com.truong.dao.PostDao;
import com.truong.entity.Post;
import com.truong.entity.Post;

@Repository("postDao")
@Transactional
public class PostDaoImpl extends AbstractDao<Integer, Post> implements PostDao{

	@Override
	public Post create(Post post) {
		 this.getSession().save(post);
		 return post;
	}

	@Override
	public void update(Post post) {
		this.getSession().update(post);
		
	}

	@Override
	public Post findOne(int id) {
		return this.getSession().find(Post.class, id);
	}

	@Override
	public List<Post> findAll() {
		CriteriaQuery<Post> criteria = this.getBuilder().createQuery(Post.class);
		Root<Post> root = criteria.from(Post.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
//		if (status >= 0) {
//			predicates.add(this.getBuilder().equal(root.get("status"), status));
//		}
		criteria.select(root).where(predicates.toArray(new Predicate[] {}))
				.orderBy(this.getBuilder().asc(root.get("id")));
		return this.getSession().createQuery(criteria).getResultList();
	}

	
}
