package com.twinkle.api.utils;

import com.twinkle.api.domain.User;
import com.twinkle.api.dto.UserDto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @author cuonglv
 *
 */
public class ObjectFactory {
	private final static MapperFacade mapper;

	static {
		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();

		mapper = mapperFactory.getMapperFacade();
	}

	public static UserDto createUserDto(User user) {
		return mapper.map(user, UserDto.class);
	}

	public static User createUser(UserDto userDto) {
		return mapper.map(userDto, User.class);
	}

//
//	public static NodeDto createNodeDto(Node node) {
//		return mapper.map(node, NodeDto.class);
//	}
//
//	public static List<NodeDto> createNodeDto(List<Node> nodes) {
//		return mapper.mapAsList(nodes, NodeDto.class);
//	}
//
//	public static Node createNode(NodeDto nodeDto) {
//		return mapper.map(nodeDto, Node.class);
//	}

}
