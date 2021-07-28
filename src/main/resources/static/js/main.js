var currentObj;
var maxId = -1;
var currentToDo;

function changeDisplay(id, action) {
	var element = document.getElementById(id);
	element.style.display = action;
}

function changeElems(id, action) {
	changeDisplay(id, action);

	var iframe = document.getElementById(id).children[0];
	var containerMessage = iframe.contentWindow.document
			.getElementById("message_place");

	var login = iframe.contentWindow.document.getElementById("login");
	var password = iframe.contentWindow.document.getElementById("password");
	var passwordConfirm = iframe.contentWindow.document
			.getElementById('passwordConfirm');

	login.style.border = "3px solid #000";
	password.style.border = "3px solid #000";

	if (passwordConfirm != null) {
		passwordConfirm.style.border = "3px solid #000";
		iframe.style.height = '340px';
		passwordConfirm.value = '';
	} else {
		iframe.style.height = '265px';
	}

	login.value = "";
	password.value = "";

	containerMessage.innerHTML = '';
	containerMessage.style.display = "none";
}

function setCorrectHeightToTextContainer() {
	var text_container = document.getElementById('format-container');
	var screenHeight = screen.height;
	var heightToShow = screen.height * 0.67;
	text_container.style.display = "flex";
	text_container.style.height = heightToShow + "px";
}

function add() {
    var id;
	if (document.getElementById('notes-container').childElementCount == 0) {
		setCorrectHeightToTextContainer();
	}

	var url = window.location.pathname.split('/')[window.location.pathname
			.split('/').length - 1];

	var xhr = new XMLHttpRequest();
	var url = window.location.pathname.split('/')[window.location.pathname
			.split('/').length - 1];
	xhr.open("POST", url, true);
	xhr.send(null);

	xhr.onreadystatechange = function() {
    		if (this.readyState != 4)
    			return;

    		if (this.status == 200) {
    			id = this.responseText;
    		}

    		if (url == "notes") {
                addNoteToPage(id, 'Title', 'Note');
            } else if (url == "toDos") {
                addToDoLineToPage(id, 'Title', null);
            }
    	}
}

function setToDoToContainer(obj) {
	currentObj = obj;
	document.getElementById('titleText').value = obj.children[0].children[0].children[0].textContent;
	document.getElementById('format-container').children[2].innerHTML = '';
	var lastContainer = obj.children[0].children[1];
	var textRedactor = document.getElementById('noteText');
	for (var i = 0; i < lastContainer.childElementCount; i++) {
		var toDo = '';
		var checked = '';
		if (lastContainer.children[i].children[0].checked) {
			checked = 'checked';
		}
		toDo += '<div onclick="setCurrentTask(this)"  id = "'
				+ lastContainer.children[i].id
				+ '"><input class="checkbox" type="checkbox" style="cursor: pointer;"'
				+ checked
				+ '><span class="formNote strikethrough" style="white-space: normal; display: inline-block; margin-bottom: 0%;" contenteditable>'
				+ removeAllTags(lastContainer.children[i].children[1].textContent)
				+ '</span></div>';
		textRedactor.insertAdjacentHTML('beforeend', toDo);

	}
}

function deleteTask() {
	document.getElementById('noteText').removeChild(currentToDo);
}

function setCurrentTask(currentTask) {
	currentToDo = currentTask;
}

function addNewToDo() {
	if (currentObj != null) {
		document.getElementById('noteText').insertAdjacentHTML(
						'beforeend',
						'<div onclick="setCurrentTask(this)"><input class="checkbox" type="checkbox"><span class="formNote span_width" style="white-space: normal; display: inline-block; margin-bottom: 0%;" contenteditable></span></div>');
	}
}

function setText(obj) {
	currentObj = obj;
	document.getElementById('titleText').value = obj.children[0].children[0].children[0].textContent;
	document.getElementById('noteText').value = obj.children[0].children[1].textContent;
}

function collectToDoLineData() {
	var ToDoLineId = currentObj.id;
	var containerToDo = document.getElementById('noteText');
	var title = removeAllTags(document.getElementById('titleText').value);
	
	const toDo = collectToDoData();
	var ToDoLine = {
			'id' : ToDoLineId,
			'title' : title,
			'toDo' : toDo
	};
	return ToDoLine;
}

function collectToDoData() {
	var containerToDo = document.getElementById('noteText');
	
	const toDo = [];
	for(var i = 0; i < containerToDo.childElementCount; i++) {
		var toDoElems = {
			'id' : 	containerToDo.children[i].id,
			'marked' : containerToDo.children[i].children[0].checked,
			'body' : removeAllTags(containerToDo.children[i].children[1].textContent)
		};
		toDo[i] = toDoElems;
	}
	return toDo;
}

function collectNoteData() {
	var noteId = currentObj.id;
	var title = removeAllTags(document.getElementById('titleText').value);
	var body = removeAllTags(document.getElementById('noteText').value);
	currentObj.children[0].children[0].children[0].textContent = title;
	currentObj.children[0].children[1].textContent = body;

	var note = {
		'id' : noteId,
		'title' : document.getElementById('titleText').value,
		'body' : document.getElementById('noteText').value
	};
	return note;
}

function saveNote() {

	var data;
	var url = window.location.pathname.split('/')[window.location.pathname.split('/').length - 1];

    var xhr = new XMLHttpRequest();

	if (url == "notes") {
	    xhr.open("POST", url + "/saveNote", true);
		data = collectNoteData();
	} else if (url == "toDos") {
	    xhr.open("POST", url + "/saveToDo", true);
		data = collectToDoLineData();
	}

	var json = JSON.stringify(data);

	xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	xhr.send(json);

	document.getElementById('notes-container').removeChild(currentObj);

	if (url == "notes") {
		addNoteToPage(currentObj.id, document.getElementById('titleText').value, document.getElementById('noteText').value);
	} else if (url == "toDos") {
		addToDoLineToPage(currentObj.id, document.getElementById('titleText').value, collectToDoData());
	}
	
	currentObj = document.getElementById(currentObj.id);
}

function deleteAllNotes() {
	var xhr = new XMLHttpRequest();
	var url = window.location.pathname.split('/')[window.location.pathname
			.split('/').length - 1];

	xhr.open("DELETE", url, true);
	xhr.send(null);

	xhr.onreadystatechange = function() {
		if (this.readyState != 4)
			return;

		if (this.status == 200) {
			changeDisplay('format-container', 'none');
			window.location.href = url;
		}
	}
}

function deleteAccount() {
	var oldPassword = document.getElementById('old_passwordForDelete');
	var currentPassword;

	var xhr = new XMLHttpRequest();
	var url = "/user";

	xhr.open("POST", url + "?password=" + oldPassword.value, true);
	xhr.responseText = 'text';
	xhr.send(null);

	xhr.onload = function() {

	if (this.status != 200) {
	    oldPassword.style.border = "3px solid #F50000";
        			addMessage('password_errorForDelete',
        					'Current password is not correct', '#F50000');
	} else {
			var xhr = new XMLHttpRequest();
			var url = "/user";

			xhr.open("DELETE", url, true);
			xhr.send(null);

			xhr.onreadystatechange = function() {
				if (this.readyState != 4) {
					return;
				}

				if (this.status == 200) {
					window.location.href = "/";
				}
			};
		}
	};
}

function deleteNote() {

	var noteId = currentObj.id;

	document.getElementById('notes-container').removeChild(currentObj);
	document.getElementById('titleText').value = "";
	document.getElementById('noteText').value = "";
	document.getElementById('noteText').innerHTML = '';

	if (document.getElementById('notes-container').childElementCount == 0) {
		changeDisplay('format-container', 'none');
	}

	var xhr = new XMLHttpRequest();
	var url = window.location.pathname.split('/')[window.location.pathname
			.split('/').length - 1];

	xhr.open("DELETE", url + "?id=" + noteId, true);
	xhr.send(null);
}

function getCookie(cookie){
    let cookies = document.cookie;
    let needed = cookies.split(cookie + "=")[1];
    alert(needed);
    return needed.split(';')[0];
}

function validate() {
    var urlValidate = window.location.pathname.split('/')[window.location.pathname.split('/').length - 1];
	var login = document.getElementById('login');
	var password = document.getElementById('password');
	let XSRF_TOKEN = getCookie('XSRF-TOKEN');

	iframe = window.top.document.getElementById('log');

	if (login.value != "") {
		if (password.value.length < 8) {
		    if(urlValidate == ""){
			    iframe.children[0].style.height = '320px';
			}
			login.style.border = "3px solid #000";
			addMessage('message_place', 'Length is less than 8 symbols',
					'#F50000');
			password.style.border = "3px solid #F50000";
		} else {
			var xhr = new XMLHttpRequest();
			var url = "/login";
            let data = "username=" + login.value + "&password=" + password.value;

            data = data.replace( '/%20/g', '+' );

			xhr.open("POST", url, true);
			xhr.setRequestHeader('X-XSRF-TOKEN', XSRF_TOKEN);
			xhr.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded; charset=utf-8' );
			xhr.send(data);

			xhr.onreadystatechange = function() {
				if (this.readyState != 4)
					return;

				if (this.status == 201) {
					window.top.location.href = "/"
				} else if (this.status == 401) {
					login.style.border = "3px solid #000";
					password.style.border = "3px solid #F50000";

					addMessage('message_place', 'Wrong password', '#F50000');
					if(urlValidate == "") {
                    	window.top.document.getElementById('log').children[0].style.height = '320px';;
                    }
				} else if (this.status == 404) {
					login.style.border = "3px solid #F50000";
					password.style.border = "3px solid #000";

					addMessage('message_place', 'No user with this login',
							'#F50000');
					if(urlValidate == "") {
                         window.top.document.getElementById('log').children[0].style.height = '320px';;
                    }
				}
			};
		}
	} else {
		login.style.border = "3px solid #F50000";
		iframe = window.top.document.getElementById('log').children[0];
		iframe.style.height = '320px';
		addMessage('message_place', 'Login is empty', '#F50000');
	}
}

function addMessage(id, message, colour) {
	var message_place = document.getElementById(id);
	changeDisplay(id, "block");
	var element = '<a style="display: flex; border: 3px solid'
			+ colour
			+ '; justify-content: center; align-items: center; font-size: 14px; height: 30px; border-radius: 5px;"><b>'
			+ message + '</b></a>';
	message_place.innerHTML = element;
}

function reg() {

	var login = document.getElementById('login');
	var password = document.getElementById('password');
	var passwordConfirm = document.getElementById('passwordConfirm');
	iframe = window.top.document.getElementById('reg').children[0];

	var xhr = new XMLHttpRequest();
	var url = "/user";

	if (login.value != "") {
		if (password.value.length < 8) {
			login.style.border = "3px solid #000";
			addMessage('message_place', 'Length is less than 8 symbols',
					'#F50000');
			password.style.border = "3px solid #F50000";
			passwordConfirm.style.border = "3px solid #F50000";

			iframe.style.height = '395px';
		} else {
			if (password.value != passwordConfirm.value) {
				login.style.border = "3px solid #000";
				password.style.border = "3px solid #F50000";
				passwordConfirm.style.border = "3px solid #F50000";
				addMessage('message_place', 'Passwords are not the same',
						'#F50000');
				iframe.style.height = '395px';
			} else {
				password.style.border = "3px solid #000";
				passwordConfirm.style.border = "3px solid #000";

				xhr.open("PUT", url + "?login=" + login.value + "&password="
						+ password.value, true);
				xhr
						.setRequestHeader("Content-Type",
								"text/text; charset=utf-8");
				xhr.send(null);

				xhr.onreadystatechange = function() {
					if (this.readyState != 4)
						return;

					if (this.status == 201) {
						window.top.location.href = "/"
					} else if (this.status == 401) {
						login.style.border = "3px solid #F50000";
						addMessage('message_place', 'Login already exist',
								'#F50000');
						iframe.style.height = '395px';
					}
				};
			}
		}
	} else {
		login.style.border = "3px solid #F50000";
		addMessage('message_place', 'Login is empty', '#F50000');
		iframe.style.height = '395px';
	}
}

function changeLogin() {
	var login = document.getElementById('login');

	if (login.value != "") {

		var xhr = new XMLHttpRequest();
		var url = "/user";

		xhr.open("PUT", url + "?login=" + login.value, true);
		xhr.setRequestHeader("Content-Type", "text/text; charset=utf-8");
		xhr.send(null);

		xhr.onreadystatechange = function() {
			if (this.readyState != 4)
				return;

			if (this.status == 200) {
				addMessage('login_error', 'Login succcesfully changed',
						'#00d419');
				login.value = "";
			} else {
				addMessage('login_error', 'Login already exist', '#F50000');
			}
		};
	} else {
		addMessage('login_error', 'Login is empty', '#F50000');
	}
}

function changePassword() {
	var oldPassword = document.getElementById('old_password');
	var newPassword = document.getElementById('new_password');
	var confNewPassword = document.getElementById('conf_new_password');
	var currentPassword;

	var xhr = new XMLHttpRequest();
	var url = "/user";

	xhr.open("POST", url + "?password=" + oldPassword.value, true);
	xhr.setRequestHeader("Content-Type", "text/text; charset=utf-8");
	xhr.responseText = 'text';
	xhr.send(null);

	xhr.onload = function() {

        if (this.status != 200) {
			oldPassword.style.border = "3px solid #F50000";
			addMessage('password_error', 'Current password is not correct',
					'#F50000');
        } else {
			oldPassword.style.border = "3px solid #000";
			if (newPassword.value.length < 8) {
				addMessage('password_error', 'Length is less than 8 symbols',
						'#F50000');
				newPassword.style.border = "3px solid #F50000";
				confNewPassword.style.border = "3px solid #F50000";
			} else {
				if (newPassword.value != confNewPassword.value) {
					addMessage('password_error',
							'New passwords are not the same', '#F50000');
					newPassword.style.border = "3px solid #F50000";
					confNewPassword.style.border = "3px solid #F50000";
				} else {
					newPassword.style.border = "3px solid #000";
					confNewPassword.style.border = "3px solid #000";

					var xhr = new XMLHttpRequest();
					var url = "/user";

					xhr.open("PUT", url + "?password=" + newPassword.value, true);
					xhr.send(null);

					xhr.onreadystatechange = function() {
						if (this.readyState != 4) {
							return;
						}

						if (this.status == 200) {
							addMessage('password_error',
									'Password succcesfully changed', '#00d419');
							oldPassword.value = "";
							newPassword.value = "";
							confNewPassword.value = "";
						}
					};
				}
			}
		}
	};
}

function exit() {
	var xhr = new XMLHttpRequest();
	var url = "/profile";

	xhr.open("POST", url + "?action=exit", true);
	xhr.setRequestHeader("Content-Type", "text/text; charset=utf-8");
	xhr.send(null);

	xhr.onreadystatechange = function() {
		if (this.readyState != 4)
			return;

		if (this.status == 200) {
			window.location.href = "/"
		}
	};
}

function getAllNotes() {
	var JsonElements;

	var xhr = new XMLHttpRequest();
	var url = window.location.pathname.split('/')[window.location.pathname
			.split('/').length - 1];

	xhr.open("GET", url + "/all", true);
	xhr.setRequestHeader("Content-Type", "text/text; charset=utf-8");
	xhr.send(null);

	xhr.onreadystatechange = function() {
		if (this.readyState != 4)
			return;

		if (this.status == 200) {
			JsonElements = this.responseText;
		}
		const Elements = JSON.parse(JsonElements);

		if (Elements.length != 0) {
			document.getElementById('format-container').style.display = "flex";
			setCorrectHeightToTextContainer();
		}

		if (url == "notes") {
			showNotes(Elements);
		} else if (url == "toDos") {
			showToDoLines(Elements);
		}
	};
}

function searchByPattern() {
	var JsonElements;
	var pattern = document.getElementById('q').value;

	if (pattern != "") {

		var xhr = new XMLHttpRequest();
		var url = window.location.pathname.split('/')[window.location.pathname
				.split('/').length - 1];
		xhr.open("GET", url + "?q=" + decodeURIComponent(pattern), true);
		xhr.setRequestHeader("Content-Type", "text/text; charset=utf-8");
		xhr.send(pattern);

		xhr.onreadystatechange = function() {
			if (this.readyState != 4)
				return;

			if (this.status == 200) {
				JsonElements = this.responseText;
			}
			const Elements = JSON.parse(JsonElements);

			if (Elements.length != 0) {
				changeDisplay('format-container', 'flex');
				setCorrectHeightToTextContainer();
			} else {
				changeDisplay('format-container', 'none');
			}
			document.getElementById('notes-container').innerHTML = '';
			document.getElementById('titleText').value = "";
			document.getElementById('noteText').value = "";

			if (url == "notes") {
				showNotes(Elements);
			} else if (url == "toDos") {
				showToDoLines(Elements);
			}
		};
	}
}

function sortByDateCreation() {
	var JsonElements;

	var xhr = new XMLHttpRequest();
	var url = window.location.pathname.split('/')[window.location.pathname
			.split('/').length - 1];
	xhr.open("GET", url + "/sortedCreation", true);
	xhr.send(null);

	xhr.onreadystatechange = function() {
		if (this.readyState != 4)
			return;

		if (this.status == 200) {
			JsonElements = this.responseText;
		}
		const Elements = JSON.parse(JsonElements);

		if (Elements.length != 0) {
			document.getElementById('format-container').style.display = "flex";
			setCorrectHeightToTextContainer();
		}
		document.getElementById('notes-container').innerHTML = '';

		if (url == "notes") {
			showNotes(Elements);
		} else if (url == "toDos") {
			showToDoLines(Elements);
		}
	};
}

function showToDoLines(ToDoLines) {
	for (var i = 0; i < ToDoLines.length; i++) {
		addToDoLineToPage(ToDoLines[i].id, ToDoLines[i].title,
				ToDoLines[i].toDo);
	}
}

function showNotes(Notes) {
	for (var i = 0; i < Notes.length; i++) {
		addNoteToPage(Notes[i].id, Notes[i].title, Notes[i].body);
	}
}

function checkSendPattern(event) {
	var filterBtns = document.getElementById('q');
	if (event.key == 'Enter' && filterBtns === document.activeElement) {
		searchByPattern();
	}
}

function getWidth() {
	if (self.innerWidth) {
		return self.innerWidth;
	}

	if (document.documentElement && document.documentElement.clientWidth) {
		return document.documentElement.clientWidth;
	}

	if (document.body) {
		return document.body.clientWidth;
	}

	if (self.innerWidth) {
		return self.innerWidth;
	}

	if (document.documentElement && document.documentElement.clientWidth) {
		return document.documentElement.clientWidth;
	}

	if (document.body) {
		return document.body.clientWidth;
	}
}

function changeStyleForScreen() {
	var filterBtns = document.getElementById('filterBtns');
	if (getWidth() >= 1000) {
		if (filterBtns != null) {
			filterBtns.style.display = "flex";
			filterBtns.style.position = "inherit";
		}
	} else {
		if (filterBtns != null) {
			filterBtns.style.position = "absolute";
		}
	}
}

function showFilterBtns() {
	var filterBtns = document.getElementById('filterBtns');

	if (filterBtns.style.display == "flex") {
		filterBtns.style.display = "none";
		filterBtns.style.position = "inherit";
	} else {
		filterBtns.style.display = "flex";
		filterBtns.style.position = "absolute";
	}
}

function removeAllTags(item) {
	if (item == null || item == "") {
		return "";
	} else {
		item.toString();
	}
	item = item.replace('<', '&lt');
	item = item.replace('>', '&gt');
	return item;
}

function addToDoLineToPage(id, title, Elements) {
	title = removeAllTags(title);
	var toDos = '';
	if (Elements != null) {
		toDos = addToDoToPage(Elements);
	}
	var ToDoLine = '<div class="note" id="'
			+ id
			+ '" onclick="setToDoToContainer(this)"> <div style="height: 90%; padding: 10px 20px;"> <p style="font-size: 20px; border-bottom: 2px solid #000; max-height: 46px; padding-bottom: 4px; margin-block-start: 10px;" > <b>'
			+ title + '</b> </p> <div class="body_note toDoLine">' + toDos
			+ '</div> </div> </div>';
	document.getElementById('notes-container').insertAdjacentHTML('afterbegin',
			ToDoLine);
}

function addToDoToPage(Elements) {
	var toDos = '';
	for (var i = 0; i < Elements.length; i++) {
		var checked = '';
		if (Elements[i].marked == true) {
			checked = 'checked';
		}
		toDos += '<div id = "' + Elements[i].id
				+ '"><input type="checkbox" disabled="disabled" style="cursor: pointer;"' + checked
				+ '><label class="strikethrough" style="cursor: pointer;">' + Elements[i].body
				+ '</label></div>';
	}
	return toDos;
}

function addNoteToPage(id, title, body) {
	title = removeAllTags(title);
	body = removeAllTags(body);
	document
			.getElementById('notes-container')
			.insertAdjacentHTML(
					'afterbegin',
					'<div class="note" id="'
							+ id
							+ '" onclick="setText(this)"> <div style="height: 90%; padding: 10px 20px;"> <p style="font-size: 20px; border-bottom: 2px solid #000; max-height: 46px; padding-bottom: 4px; margin-block-start: 10px;" > <b>'
							+ title + '</b> </p> <p class="body_note">' + body
							+ '</p> </div> </div>');

}