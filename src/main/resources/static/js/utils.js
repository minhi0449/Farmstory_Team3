// fetch GET용
async function fetchGet(url){

    console.log("fetchData1...1");

    try{
        console.log("fetchData1...2");
        const response = await fetch(url);
        console.log("here1");

        if(!response.ok){
            console.log("here2");
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}

// fetch POST용
async function fetchPost(url, jsonData){

    console.log("fetchData2...1");

    try{
        console.log("fetchData2...2");
        const response = await fetch(url, {
            method: 'POST',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });
        console.log("fetchData2...3");

        if(!response.ok){
            console.log("fetchData2...4");
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("fetchData2...5 : " + data);

        return data;

    }catch (err) {
        console.log(err)
        return null;
    }
}

// fetch DELETE용
async function fetchDelete(url){

    try{
        const response = await fetch(url, {
            method: 'DELETE'
        });

        if(!response.ok){
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}


// fetch PUT용
async function fetchPut(url, jsonData){

    try{
        const response = await fetch(url, {
            method: 'PUT',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });

        if(!response.ok){
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}


function alertModal(message){
    const modal = document.getElementById('alertModal');
    modal.getElementsByClassName('modal-body')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show();
}

function confirmModal(message){
    return new Promise(function (resolve, reject){
        const confirmModal = document.getElementById('confirmModal');
        const btnOk = document.getElementById('btnOk');
        const btnCancel = document.getElementById('btnCancel');
        confirmModal.getElementsByClassName('modal-body')[0].innerText = message;

        const modal = new bootstrap.Modal(confirmModal);
        modal.show(); // 모달 열기

        // 확인 버튼 클릭 시
        btnOk.addEventListener('click', function (){
            resolve(true);
        });

        // 취소 버튼 클릭 시
        btnCancel.addEventListener('click', function (){
            resolve(false);
        });
    });

}

function showResultValid(result, message){
    result.style.color = 'green';
    result.innerText = message;
}

function showResultInvalid(result, message){
    result.style.color = 'red';
    result.innerText = message;
}

function postcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                //document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                //document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('inputZip').value = data.zonecode;
            document.getElementById("inputAddr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("inputAddr2").focus();
        }
    }).open();
}