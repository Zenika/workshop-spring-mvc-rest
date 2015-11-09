/**
 * 
 */
package com.zenika.web;

import java.util.Iterator;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * @author acogoluegnes
 *
 */
// TODO 16 analyser la classe PageResource
// elle :
//   - implémente Page (interface Spring JPA) et délègue tous les appels à la page à exposer
//   - hérite de ResourceSupport (Spring HATEOAS) et supporte donc les Links
public class PageResource<T> extends ResourceSupport implements Page<T> {
	
	private final Page<T> page;
	
	/**
	 * 
	 * @param page la page à exposer
	 * @param pageParam le nom du paramètre HTTP pour l'index de la page
	 * @param sizeParam le nom du paramètre HTTP pour la taille des pages
	 */
	public PageResource(Page<T> page, String pageParam,
			String sizeParam) {
		super();
		this.page = page;
		// TODO 17 analyser comment le lien vers la page précédente est créé
		if(page.hasPrevious()) {
			String path = createBuilder()
				.queryParam(pageParam,page.getNumber()-1)
				.queryParam(sizeParam,page.getSize())
				.build()
				.toUriString();
			Link link = new Link(path, Link.REL_PREVIOUS);
			add(link);
		}
		// TODO 18 créer le lien pour la page suivante
		
		
		// TODO 19 regarder les différents liens créés et ajoutés
		Link link = buildPageLink(pageParam,0,sizeParam,page.getSize(),Link.REL_FIRST);
		add(link);
		
		int indexOfLastPage = page.getTotalPages() - 1;
		link = buildPageLink(pageParam,indexOfLastPage,sizeParam,page.getSize(),Link.REL_LAST);
		add(link);
		
		link = buildPageLink(pageParam,page.getNumber(),sizeParam,page.getSize(),Link.REL_SELF);
		add(link);
	}
	
	private ServletUriComponentsBuilder createBuilder() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri();
	}
	
	private Link buildPageLink(String pageParam,int page,String sizeParam,int size,String rel) {
		String path = createBuilder()
				.queryParam(pageParam,page)
				.queryParam(sizeParam,size)
				.build()
				.toUriString();
		Link link = new Link(path,rel);
		return link;
	}
	
	@Override
	public Pageable nextPageable() {
		return null;
	}
	
	@Override
	public Pageable previousPageable() {
		return null;
	}
	
	@Override
	public int getNumber() {
		return page.getNumber();
	}

	@Override
	public int getSize() {
		return page.getSize();
	}

	@Override
	public int getTotalPages() {
		return page.getTotalPages();
	}

	@Override
	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}

	@Override
	public long getTotalElements() {
		return page.getTotalElements();
	}

	@Override
	public boolean hasPrevious() {
		return page.hasPrevious();
	}

	@Override
	public boolean isFirst() {
		return page.isFirst();
	}

	@Override
	public boolean hasNext() {
		return page.hasNext();
	}

	@Override
	public boolean isLast() {
		return page.isLast();
	}

	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}

	@Override
	public List<T> getContent() {
		return page.getContent();
	}

	@Override
	public boolean hasContent() {
		return page.hasContent();
	}

	@Override
	public Sort getSort() {
		return page.getSort();
	}
	
	@Override
	public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
		throw new UnsupportedOperationException();
	}
	
}
