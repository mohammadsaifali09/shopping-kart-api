package org.jsp.api.service;

import java.util.Optional;

import org.jsp.api.dao.AddressDao;
import org.jsp.api.dao.UserDao;
import org.jsp.api.dto.Address;
import org.jsp.api.dto.ResponseStructure;
import org.jsp.api.dto.User;
import org.jsp.api.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	@Autowired
	private AddressDao aDao;

	@Autowired
	private UserDao uDao;

	public ResponseEntity<ResponseStructure<Address>> saveAddress(Address address, int user_id) {
		Optional<User> recUser = uDao.findById(user_id);
		if (recUser.isPresent()) {
			recUser.get().getAddresses().add(address);
			address.setUser(recUser.get());
			ResponseStructure<Address> structure = new ResponseStructure<>();
			structure.setData(aDao.saveAddress(address));
			structure.setMessage("Address saved succesfully");
			structure.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Address>>(structure, HttpStatus.CREATED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Address>> updateAddress(Address address, int user_id) {
		Optional<User> recUser = uDao.findById(user_id);
		if (recUser.isPresent()) {
			recUser.get().getAddresses().add(address);
			address.setUser(recUser.get());
			ResponseStructure<Address> structure = new ResponseStructure<>();
			structure.setData(aDao.updateAddress(address));
			structure.setMessage("Address updated succesfully");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Address>>(structure, HttpStatus.ACCEPTED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Address>> findAddressById(int id) {
		ResponseStructure<Address> structure = new ResponseStructure<>();
		Optional<Address> recAddress = aDao.findById(id);
		if (recAddress.isEmpty()) {
			structure.setData(null);
			structure.setMessage("Address not found");
			structure.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<Address>>(structure, HttpStatus.NOT_FOUND);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<String>> deleteAddress(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<Address> recAddress = aDao.findById(id);
		if (recAddress.isEmpty()) {
			structure.setData("Address not found");
			structure.setMessage("Address not deleted");
			structure.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
		}
		throw new IdNotFoundException();
	}

//	public ResponseEntity<ResponseStructure<List<Address>>> findAddressByUserId(int id) {
//		ResponseStructure<List<Address>> structure = new ResponseStructure<>();
//		structure.setBody(aDao.findAddressByUserId(id));
//		structure.setMessage("The list of address for the user");
//		structure.setStatusCode(HttpStatus.OK.value());
//		return new ResponseEntity<ResponseStructure<List<Address>>>(structure, HttpStatus.OK);
//	}
}
