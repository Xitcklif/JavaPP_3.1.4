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
		await setRoleList()
		await fetch("/api/users")
			.then(response => response.json())
			.then(users => {
				users.forEach(user => {
					temp +=
						`
							<tr>
								<td>${user.id}</td>
								<td>${user.username}</td>
								<td>${user.roles.map(r => " " + r.name)
							.map(name => name.replace("ROLE_", ""))}</td>
								<td>
									<buttonEdit class="btn btn-primary" data-toggle="modal" id="${user.id}" href="#modal">Edit</buttonEdit>
								</td>
								<td>
									<buttonDelete class="btn btn-danger" data-toggle="modal" id="${user.id}" href="#modal">Delete</buttonDelete>
								</td>
							</tr>
						`
				})
				table.innerHTML = temp
			})
	}
	let usersTable = document.querySelector('#usersTable')
	if (usersTable !== null) {
		let editModalButtons = usersTable.getElementsByTagName('buttonEdit')
		let deleteModalButtons = usersTable.getElementsByTagName('buttonDelete')
		for (let i = 0; i < editModalButtons.length; i++) {
			editModalButtons[i].addEventListener('click', () => {
				editUserModal(editModalButtons[i].id)
			})
		}
		for (let i = 0; i < deleteModalButtons.length; i++) {
			deleteModalButtons[i].addEventListener('click', () => {
				deleteUserModal(deleteModalButtons[i].id)
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
				<td>${user.roles.map(r => " " + r.name)
				.map(name => name.replace("ROLE_", ""))}</td>
			</tr>
			`
			document.querySelector('#userTable tbody').innerHTML = temp;

			temp = ``
			temp += `
			${user.username} with roles ${user.roles.map(r => " " + r.name).map(name => name.replace("ROLE_", ""))}
			`
			document.getElementById('userWithRoles').innerHTML = temp
		})
}

async function addUser() {
	const addButton = document.querySelector('#addUserButton')
	if (addButton !== null) {
		const list = document.getElementsByClassName("checkboxList")[0]
			.getElementsByTagName("input")
		addButton.addEventListener('click', async () => {
			let checkedRoles = ""
			for (let i=0; i < list.length; i++) {
				if (list[i].checked) {
					checkedRoles += "ROLE_" + list[i].name + " "
				}
			}
			let addUserData = {
				username: document.querySelector('#username').value,
				password: document.querySelector('#password').value,
				confPass: document.querySelector('#cPassword').value,
				rolesList: checkedRoles
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
							for (let i=0; i < list.length; i++) {
								list[i].checked = false
							}
							getUsers()
							document.querySelector('#all-users-tab').click()
						}
					})
			}
		})
	}
}

async function editUserModal(id) {
	if (id !== undefined) {
		let temp = `<div class="form-group">
						<label class="font-weight-bold mb-0" for="cpw">Confirm password</label>
						<div class="d-flex justify-content-center">
							<input type="text" class="form-control w-50" id="cpw" name="confPass">
						</div>
					</div>
					<div class="form-group d-flex justify-content-center">
						<div class="modalRolesCheckbox" style="width:120px; height:62px; overflow:auto; border:solid 1px #ccd2d9;">`
		document.querySelector(`#modal h5`).textContent = "Edit user"
		const user = await fetch(`/api/users/${id}`)
			.then(response => response.json())
		let modal = document.querySelector('#modal')
		await fetch("/api/roles")
			.then(response => response.json())
			.then(roles => {
				roles.forEach(role => {
					temp +=
						`
						<div><input type="checkbox" name="${role.name.replace("ROLE_", "")}">
							${role.name.replace("ROLE_", "")}
						</input></div>
						`
				})
			})
		temp += `</div></div>`
		document.querySelector(`#modal modalRoles`).innerHTML = temp
		document.getElementsByClassName(`modal-footer`)[0].innerHTML =
			`
				<button class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button class="btn btn-success">Edit</button>
			`
		modal.querySelector('#id').value = user.id
		modal.querySelector('#id').readOnly = true
		modal.querySelector('#un').value = user.username

		let rolesListFromUser = user.roles.map(r => r.name)
			.map(name => name.replace("ROLE_", ""))
		let modalRolesCheckbox = modal.getElementsByClassName('modalRolesCheckbox')[0]
			.getElementsByTagName('input')
		for (let i=0; i<modalRolesCheckbox.length; i++) {
			for (let j=0; j<rolesListFromUser.length; j++) {
				if(modalRolesCheckbox[i].name === rolesListFromUser[j]) {
					modalRolesCheckbox[i].checked = "checked"
				}
			}
		}

		let buttons = modal.getElementsByTagName('button')
		for (let i = 0; i < buttons.length; i++) {
			buttons[i].addEventListener('click', async () => {
				if (buttons[i].className === "btn btn-success") {
					await editUser(id)
					modal.getElementsByClassName("btn btn-secondary")[0].click()
				} else if (buttons[i].className === "btn btn-secondary" ||
					buttons[i].className === "close") {
					await clearModal(modal)
				}
			})
		}
	}
}

async function editUser(id) {
	if (id !== undefined) {
		const list = document.getElementsByClassName("modalRolesCheckbox")[0]
			.getElementsByTagName("input")
		let checkedRoles = ""
		for (let i=0; i < list.length; i++) {
			if (list[i].checked) {
				checkedRoles += "ROLE_" + list[i].name + " "
			}
		}
		let modal = document.querySelector('#modal')
		let editUser = {
			id: modal.querySelector('#id').value,
			username: modal.querySelector('#un').value,
			password: modal.querySelector('#pw').value,
			confPass: modal.querySelector('#cpw').value,
			rolesList: checkedRoles
		}
		await usersFetch.editUser(editUser)
			.then(response => {
				if (response.ok) {
					getUsers()
				}
			})
	}
}

async function deleteUserModal(id) {
	if (id !== undefined) {
		document.querySelector(`#modal h5`).textContent = "Delete user"
		const user = await fetch(`/api/users/${id}`)
			.then(response => response.json())
		let modal = document.querySelector('#modal')
		document.querySelector(`#modal modalRoles`).innerHTML =
			`
				<div class="form-group">
					<label class="font-weight-bold mb-0" for="rolesList">Roles</label>
					<div class="d-flex justify-content-center">
						<input type="text" class="form-control w-50" id="rolesList">
					</div>
				</div>
			`
		document.getElementsByClassName(`modal-footer`)[0].innerHTML =
			`
				<button class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button class="btn btn-danger">Delete</button>
			`
		modal.querySelector('#id').value = user.id
		modal.querySelector('#id').readOnly = true
		modal.querySelector('#un').value = user.username
		modal.querySelector('#un').readOnly = true
		modal.querySelector('#pw').value = user.password
		modal.querySelector('#pw').readOnly = true
		modal.querySelector('#rolesList').value = user.roles
			.map(r => " " + r.name)
			.map(name => name.replace("ROLE_", ""))
		modal.querySelector('#rolesList').readOnly = true
		let buttons = modal.getElementsByTagName('button')
		for (let i = 0; i < buttons.length; i++) {
			buttons[i].addEventListener('click', async () => {
				if (buttons[i].className === "btn btn-danger") {
					await deleteUser(id)
					modal.getElementsByClassName("btn btn-secondary")[0].click()
				} else if (buttons[i].className === "btn btn-secondary" ||
					buttons[i].className === "close") {
					await clearModal(modal)
				}
			})
		}
	}
}

async function deleteUser(id) {
	if (id !== undefined) {
		await usersFetch.deleteUser(id)
			.then(response => {
				if (response.ok) {
					getUsers()
				}
			})
	}
}

async function clearModal(modal) {
	modal.querySelector('#un').readOnly = false
	modal.querySelector('#pw').readOnly = false
	modal.querySelector('#pw').value = ""
	modal.querySelector('#cpw').value = ""
}

async function setRoleList() {
	let rolesList = ``
	const list = document.getElementsByClassName("checkboxList")[0]
	if (list !== null) {
		await fetch("/api/roles")
			.then(response => response.json())
			.then(roles => {
				roles.forEach(role => {
					rolesList +=
						`
						<div><input type="checkbox" name="${role.name.replace("ROLE_", "")}">
							${role.name.replace("ROLE_", "")}
						</input></div>
						`
				})
			})
		list.innerHTML = rolesList
	}
}