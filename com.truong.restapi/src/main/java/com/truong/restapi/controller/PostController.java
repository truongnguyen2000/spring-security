package com.truong.restapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truong.common.exception.CustomException;
import com.truong.common.utils.BaseResponse;
import com.truong.entity.Employee;
import com.truong.entity.Post;
import com.truong.restapi.request.PostRequest;
import com.truong.restapi.response.PostResponse;
import com.truong.service.PostService;

@RestController
@RequestMapping("api/v1/post")
public class PostController extends BaseController {

	@Autowired
	PostService postService;

	@PostMapping("create")
	public ResponseEntity<BaseResponse<PostResponse>> create(@Valid @RequestBody PostRequest postRequest)
			throws CustomException {

		BaseResponse<PostResponse> respponse = new BaseResponse<>();

		Employee employee = this.getUser();

		if (employee == null) {
			respponse.setStatus(HttpStatus.UNAUTHORIZED);
			respponse.setMessage(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(respponse, HttpStatus.OK);
		}

		Post post = new Post();
		post.setTitle(postRequest.getTitle());
		post.setContent(postRequest.getContent());
		post.setEmployeeId(employee.getId());

		respponse.setData(new PostResponse(postService.create(post)));

		return new ResponseEntity<>(respponse, HttpStatus.OK);
	}

	@PostMapping("{id}/update")
	public ResponseEntity<BaseResponse<PostResponse>> update(@PathVariable(name = "id") int id,
			@RequestBody PostRequest postRequest) throws CustomException {

		BaseResponse<PostResponse> respponse = new BaseResponse<>();

		Employee employee = this.getUser();

		if (employee == null) {
			respponse.setStatus(HttpStatus.UNAUTHORIZED);
			respponse.setMessage(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(respponse, HttpStatus.OK);
		}

		Post post = postService.findOne(id);

		if (post == null) {
			respponse.setStatus(HttpStatus.BAD_REQUEST);
			respponse.setMessageError("Bài viết không tồn tại");
			return new ResponseEntity<>(respponse, HttpStatus.OK);
		}

		post.setTitle(postRequest.getTitle());
		post.setContent(postRequest.getContent());
		post.setEmployeeId(employee.getId());
		
		postService.update(post);

		respponse.setData(new PostResponse(post));

		return new ResponseEntity<>(respponse, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<List<PostResponse>>> getAll(
			@RequestParam(name = "status", defaultValue = "-1") int status
			) throws Exception {

		BaseResponse<List<PostResponse>> respponse = new BaseResponse<>();

		if (this.getUser() == null) {
			respponse.setStatus(HttpStatus.UNAUTHORIZED);
			respponse.setMessage(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(respponse, HttpStatus.OK);
		}

		respponse.setData(new PostResponse().mapToList(postService.findAll(status)));

		return new ResponseEntity<>(respponse, HttpStatus.OK);

	}

	@GetMapping("{id}")
	public ResponseEntity<BaseResponse<PostResponse>> getDetail(@PathVariable(name = "id") int id)
			throws CustomException {

		BaseResponse<PostResponse> respponse = new BaseResponse<>();

		if (this.getUser() == null) {
			respponse.setStatus(HttpStatus.UNAUTHORIZED);
			respponse.setMessage(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(respponse, HttpStatus.OK);
		}

		Post post = postService.findOne(id);

		if (post == null) {
			respponse.setStatus(HttpStatus.BAD_REQUEST);
			respponse.setMessageError("Bài viết không tồn tại");
			return new ResponseEntity<>(respponse, HttpStatus.OK);
		}

		respponse.setData(new PostResponse(post));

		return new ResponseEntity<>(respponse, HttpStatus.OK);

	}
}
