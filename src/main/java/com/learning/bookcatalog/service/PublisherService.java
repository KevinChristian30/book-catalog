package com.learning.bookcatalog.service;

import com.learning.bookcatalog.domain.Publisher;
import com.learning.bookcatalog.dto.PublisherCreateRequestDTO;
import com.learning.bookcatalog.dto.PublisherListResponseDTO;
import com.learning.bookcatalog.dto.PublisherResponseDTO;
import com.learning.bookcatalog.dto.PublisherUpdateRequestDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;

public interface PublisherService {
	public void createPublisher(PublisherCreateRequestDTO dto);
	
	public PublisherUpdateRequestDTO getPublisher(String publisherId);
	
	public void updatePublisher(String publisherId, PublisherUpdateRequestDTO Dto);

	public ResultResponsePageDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit, String sortBy, String direction, String publisherName);

	public Publisher findPublisher(String publisherId);
	
	public PublisherResponseDTO constructDTO(Publisher publisher);
}
