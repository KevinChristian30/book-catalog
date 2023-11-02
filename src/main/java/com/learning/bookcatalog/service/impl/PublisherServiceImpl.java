package com.learning.bookcatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learning.bookcatalog.domain.Publisher;
import com.learning.bookcatalog.dto.PublisherCreateRequestDTO;
import com.learning.bookcatalog.dto.PublisherListResponseDTO;
import com.learning.bookcatalog.dto.PublisherResponseDTO;
import com.learning.bookcatalog.dto.PublisherUpdateRequestDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;
import com.learning.bookcatalog.exception.BadRequestException;
import com.learning.bookcatalog.repository.PublisherRepository;
import com.learning.bookcatalog.service.PublisherService;
import com.learning.bookcatalog.util.PaginationUtil;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {
	private final PublisherRepository publisherRepository;

	@Override
	public void createPublisher(PublisherCreateRequestDTO dto) {
		Publisher publisher = new Publisher();

		publisher.setName(dto.getPublisherName());
		publisher.setCompanyName(dto.getCompanyName());
		publisher.setAddress(dto.getAddress());
		publisher.setDeleted(false);

		publisherRepository.save(publisher);
	}

	@Override
	public PublisherUpdateRequestDTO getPublisher(String publisherId) {
		Publisher publisher = publisherRepository.findBySecureId(publisherId)
				.orElseThrow(() -> new BadRequestException("NOT FOUND"));

		PublisherUpdateRequestDTO dto = new PublisherUpdateRequestDTO();
		dto.setAddress(publisher.getAddress());
		dto.setCompanyName(publisher.getCompanyName());
		dto.setPublisherName(publisher.getName());

		return dto;
	}

	@Override
	public void updatePublisher(String publisherId, PublisherUpdateRequestDTO dto) {
		Publisher publisher = publisherRepository.findBySecureId(publisherId)
				.orElseThrow(() -> new BadRequestException("invalid.publisher"));

		publisher.setName(dto.getPublisherName() == null || dto.getPublisherName().isBlank() ? publisher.getName()
				: dto.getPublisherName());
		publisher.setCompanyName(
				dto.getCompanyName() == null || dto.getCompanyName().isBlank() ? publisher.getCompanyName()
						: dto.getCompanyName());
		publisher.setAddress(dto.getAddress());

		publisherRepository.save(publisher);
	}

	@Override
	public ResultResponsePageDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit,
			String sortBy, String direction, String publisherName) {
		publisherName = StringUtils.isBlank(publisherName) ? "%" : publisherName + "%";

		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = (Pageable) PageRequest.of(pages, limit, sort);

		Page<Publisher> pageResult = publisherRepository.findByNameLikeIgnoreCase(publisherName, pageable);

		List<PublisherListResponseDTO> dtos = pageResult.stream().map(page -> {
			PublisherListResponseDTO dto = new PublisherListResponseDTO();

			dto.setPublisherId(page.getSecureId());
			dto.setPublisherName(page.getName());
			dto.setCompanyName(page.getCompanyName());

			return dto;
		}).collect(Collectors.toList());

		return PaginationUtil.createResultPage(dtos, pageResult.getTotalElements(), (long) pageResult.getTotalPages());
	}

	@Override
	public Publisher findPublisher(String publisherId) {
		return publisherRepository.findBySecureId(publisherId)
				.orElseThrow(() -> new BadRequestException("invalid.publisher"));
	}

	@Override
	public PublisherResponseDTO constructDTO(Publisher publisher) {
		PublisherResponseDTO dto = new PublisherResponseDTO();
		
		dto.setPublisherId(publisher.getSecureId());
		dto.setPublisherName(publisher.getName());
		
		return dto;
	}
}
