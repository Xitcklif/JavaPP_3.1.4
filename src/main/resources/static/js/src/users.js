$(async () => {
	await getUsers()
	await getCurrentUser()
	await addUser()
})

const usersFetch = {
	head: {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Referer': null
	},
	addUser: async (user) => await fetch('/api/users', {
		method: 'POST',
		headers: usersFetch.head,
		body: JSON.stringify(user)
	}),
	editUser: async (user) => await fetch(`/api/users`, {
		method: 'PUT',
		headers: usersFetch.head,
		body: JSON.stringify(user)
	}),
	deleteUser: async (id) => await fetch(`/api/users/${id}`, {
		method: 'DELETE',
		headers: usersFetch.head
	})
}

async function getUsers() {
	let temp = ``
	const table = document.querySelector('#usersTable tbody')
	if (table !== null) {
		await fetch("/api/users")
			.then(response => response.json())
			.then(users => {
				users.forEach(user => {
					temp += `
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.roles.map(r => " " + r.name)}</td>
					<td>
						<button class="btn btn-primary" data-toggle="modal" href="#editModal-${user.id}">Edit</button>
						<div class="modal fade" id="editModal-${user.id}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
						
									<div class="modal-header">
										<h5 class="modal-title">Edit user</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
						
									<div>
									
										<div class="modal-body text-center">
											<div class="form-group">
												<label class="font-weight-bold mb-0" for="id">ID</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50" id="id"
													value="${user.id}" th:name="id" readonly>
												</div>
											</div>
											<div class="form-group">
												<label class="font-weight-bold mb-0" for="un">Username</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50" id="un" 
													value="${user.username}" th:name="username">
												</div>
											</div>
											<div class="form-group">
												<label class="font-weight-bold mb-0" for="pw">New password</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50" id="pw" th:name="password">
												</div>
											</div>
											<div class="form-group">
												<label class="font-weight-bold mb-0" for="cpw">Confirm password</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50" id="cpw" th:name="confPass">
												</div>
											</div>
											<div class="form-group d-flex justify-content-center">
												<div class="mt-3">
													<label class="form-check-label font-weight-bold">
													<input class="form-check-input" type="checkbox" 
													th:name="roleAdmin" id="editCheckbox">Admin</label>
												</div>
											</div>
										</div>
						
										<div class="modal-footer">
											<button class="btn btn-secondary" data-dismiss="modal" id="close">Close</button>
											<button class="btn btn-success" id="editUserButton" name="${user.id}">Edit</button>
										</div>
										
									</div>
								</div>
							</div>
						</div>
					</td>
					<td>
						<button class="btn btn-danger" data-toggle="modal" href="#deleteModal-${user.id}">Delete</button>
						<div class="modal fade" id="deleteModal-${user.id}" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
						
									<div class="modal-header">
										<h5 class="modal-title">Delete user</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
						
									<div>
										<div class="modal-body text-center">
						
											<div class="form-group">
												<label class="font-weight-bold mb-0" for="idDel">ID</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50"
													id="idDel" value="${user.id}" readonly>
												</div>
											</div>
						
											<div class="form-group">
												<label class="font-weight-bold mb-0" for="unDel">Username</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50"
													id="unDel" value="${user.username}" readonly>
												</div>
											</div>
						
											<div class="form-group">
												<label class="font-weight-bold mb-0"
												       for="pwDel">Password</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50"
													id="pwDel" value="${user.password}" readonly>
												</div>
											</div>
						
											<div class="form-group">
												<label class="font-weight-bold mb-0"
												       for="rolesDel">Roles</label>
												<div class="d-flex justify-content-center">
													<input type="text" class="form-control w-50" id="rolesDel"
													value="${user.roles.map(r => " " + r.name)}" readonly>
												</div>
											</div>
						
										</div>
						
										<div class="modal-footer">
											<button class="btn btn-secondary" data-dismiss="modal" id="close">Close</button>
											<a class="btn btn-danger" id="deleteUserButton" name="${user.id}">Delete</a>
										</div>
						
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				`
				})
				table.innerHTML = temp
			})
	}
	let usersTable = document.querySelector('#usersTable')
	if (usersTable !== null) {
		let editButtons = usersTable.getElementsByClassName('btn-success')
		let deleteButtons = usersTable.getElementsByTagName('a')
		for (let i = 0; i < editButtons.length; i++) {
			editButtons[i].addEventListener('click', () => {
				editUser(editButtons[i].name)
			})
		}
		for (let i = 0; i < deleteButtons.length; i++) {
			deleteButtons[i].addEventListener('click', () => {
				deleteUser(deleteButtons[i].name)
			})
		}
	}
}

async function getCurrentUser() {
	let temp = ``
	await fetch(`/api/user`)
		.then(response => response.json())
		.then(user => {
			temp += `
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.roles.map(r => " " + r.name)}</td>
			</tr>
			`
			document.querySelector('#userTable tbody').innerHTML = temp;

			temp = ``
			temp += `
			${user.username} with roles ${user.roles.map(r => " " + r.name)}
			`
			document.getElementById('userWithRoles').innerHTML = temp
		})
}

async function addUser() {
	const addButton = document.querySelector('#addUserButton')
	if (addButton !== null) {
		addButton.addEventListener('click', async () => {
			let addUserData = {
				username: document.querySelector('#username').value,
				password: document.querySelector('#password').value,
				confPass: document.querySelector('#cPassword').value,
				isAdmin: document.querySelector('#checkboxAdmin').checked
			}
			if (addUserData.username === "" ||
				addUserData.password === "" ||
				addUserData.confPass === "") {
				alert("Enter values in all fields!")
			} else {
				await usersFetch.addUser(addUserData)
					.then(response => {
						if (response.ok) {
							document.querySelector('#username').value = ""
							document.querySelector('#password').value = ""
							document.querySelector('#cPassword').value = ""
							document.querySelector('#checkboxAdmin').checked = false
							getUsers()
							document.querySelector('#all-users-tab').click()
						}
					})
			}
		})
	}
}

async function editUser(userId) {
	if (userId !== undefined) {
		let modal = document.querySelector("#editModal-" + userId)
		let editUser = {
			id: modal.querySelector('#id').value,
			username: modal.querySelector('#un').value,
			password: modal.querySelector('#pw').value,
			confPass: modal.querySelector('#cpw').value,
			isAdmin: modal.querySelector('#editCheckbox').checked
		}
		await usersFetch.editUser(editUser)
			.then(response => {
				if (response.ok) {
					modal.querySelector('#close').click()
					getUsers()
				}
			})
	}
}

async function deleteUser(userId) {
	if (userId !== undefined) {
		let modal = document.querySelector("#deleteModal-" + userId)
		await usersFetch.deleteUser(userId)
			.then(response => {
				if (response.ok) {
					modal.querySelector('#close').click()
					getUsers()
				}
			})
	}
}