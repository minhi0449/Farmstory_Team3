
    //유효성 검사에 사용할 상태변수
    let isUidOk   = false;
    let isPassOk  = false;
    let isNameOk  = false;
    let isNickOk  = false;
    let isEmailOk = false;
    let isHpOk    = false;

    // 유효성 검사에 사용할 정규표현식
    const reUid   = /^[a-z]+[a-z0-9]{4,19}$/g;
    const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
    const reName  = /^[가-힣]{2,10}$/
    const reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
    const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

    window.onload = function (){

        // 아이디 유효성 검사
        const inputUids = document.getElementsByClassName('inputUid');
        const resultUid = document.getElementById('resultUid');
        const btnCheckUid = document.getElementById('btnCheckUid');

        btnCheckUid.onclick = function(){
            const type    = this.dataset.type;
            const value   = inputUids[0].value;
            console.log('type : ' + type + ', value : ' + value);

            // 정규식 검사
            if(!value.match(reUid)){
                showResultInvalid(resultUid, '아이디 형식이 맞지 않습니다.')
                isUidOk = false;
                return;
            }

            setTimeout(async () => {
                const data = await fetchGet(`/farmstory/user/${type}/${value}`);
                if(data.result > 0){
                    showResultInvalid(resultUid, '이미 사용 중인 아이디 입니다.');
                    isUidOk = false;
                }else{
                    showResultValid(resultUid, '사용 가능한 아이디 입니다.');
                    isUidOk = true;
                }
            }, 1000);
        }

        // 비밀번호 유효성 검사
        const inputPasses = document.getElementsByClassName('inputPass');
        const resultPass = document.getElementById('resultPass');

        inputPasses[1].addEventListener('focusout', ()=>{

            if(inputPasses[0].value == inputPasses[1].value){

                if(!inputPasses[0].value.match(rePass)){
                    showResultInvalid(resultPass, '비밀번호 형식에 맞지 않습니다.');
                    isPassOk = false;
                }else{
                    showResultValid(resultPass, '사용 가능한 비밀번호 입니다.');
                    isPassOk = true;
                }
            }else{
                showResultInvalid(resultPass, '비밀번호가 일치하지 않습니다.');
                isPassOk = false;
            }
        });

        // 이름 유효성 검사
        const inputNames = document.getElementsByClassName('inputName');
        const resultName = document.getElementById('resultName');

        inputNames[0].addEventListener('focusout', ()=>{

            const value = inputNames[0].value;

            if(!value.match(reName)){
                showResultInvalid(resultName, '이름 형식이 맞지 않습니다.');
                isNameOk = false;
            }else{
                showResultValid(resultName, '');
                isNameOk = true;
            }
        });

        // 별명 유효성 검사
        const inputNick = document.getElementsByClassName('inputNick')[0];
        const resultNick = document.getElementById('resultNick');
        const btnCheckNick = document.getElementById('btnCheckNick');

        btnCheckNick.onclick = function() {
            const type       = this.dataset.type;
            const value      = inputNick.value;
            console.log('type : ' + type + ', value : ' + value);

            // 정규식 검사
            if(!value.match(reNick)){
                showResultInvalid(resultNick, '닉네임 형식이 맞지 않습니다.');
                isNickOk = false;
                return;
            }

            setTimeout(async () => {

                const data = await fetchGet(`/farmstory/user/${type}/${value}`);

                if(data.result > 0){
                    showResultInvalid(resultNick, '이미 사용중인 닉네임 입니다.');
                    isNickOk = false;
                }else{
                    showResultValid(resultNick, '사용 가능한 닉네임 입니다.');
                    isNickOk = true;
                }
            }, 1000);
        }



        // 이메일 유효성 검사
        const inputEmail = document.getElementsByClassName('inputEmail')[0];
        const resultEmail = document.getElementById('resultEmail');
        const btnCheckEmail = document.getElementById('btnCheckEmail');
        const auth = document.getElementsByClassName('auth')[0];

        btnCheckEmail.onclick = function(){
            const type      = this.dataset.type;
            const value     = inputEmail.value;

            // 유효성 검사
            if(!value.match(reEmail)){
                showResultInvalid(resultEmail, '이메일 형식이 맞지 않습니다.');
                isEmailOk = false;
                return;
            }

            // 이메일 인증코드 발급 및 중복체크
            setTimeout(async () => {
                const data = await fetchGet(`/farmstory/user/${type}/${value}`);
                console.log('data : ' + data.result);

                if(data.result > 0){
                    showResultInvalid(resultEmail, '이미 사용중인 이메일 입니다.');
                    isEmailOk = false;
                }else{
                    showResultValid(resultEmail, '인증코드가 발송 되었습니다.');
                    // 인증코드 입력 필드 활성화
                    auth.style.display = 'block';

                    isEmailOk = false;
                }
            }, 1000);
        }

        // 이메일 인증코드 확인
        const inputEmailCode = document.getElementsByClassName('inputEmailCode')[0];
        const btnCheckEmailCode = document.getElementById('btnCheckEmailCode');

        btnCheckEmailCode.onclick = async function (){

            const jsonData = {"code": inputEmailCode.value};

            const data = await fetchPost(`/farmstory/email`, jsonData);

            if(!data.result){
                showResultInvalid(resultEmail, '인증코드가 일치하지 않습니다.');
                isEmailOk = false;
            }else{
                showResultValid(resultEmail, '이메일이 인증되었습니다.');
                isEmailOk = true;
            }
        }

        // 휴대폰 유효성 검사
        const inputHp = document.getElementsByClassName('inputHp')[0];
        const resultHp = document.getElementById('resultHp');
        const btnCheckHp = document.getElementById('btnCheckHp');

        btnCheckHp.onclick = function() {
            const type      = this.dataset.type;
            const value     = inputHp.value;

            // 정규식 검사
            if(!value.match(reHp)){
                showResultInvalid(resultHp, '휴대폰 형식이 맞지 않습니다.');
                isHpOk = false;
                return;
            }

            setTimeout(async () => {
                const data = await fetchGet(`/farmstory/user/${type}/${value}`);

                if(data.result > 0){
                    showResultInvalid(resultHp, '이미 사용중인 휴대폰 입니다.');
                    isHpOk = false;
                }else{
                    showResultValid(resultHp, '사용 가능한 휴대폰 입니다.');
                    isHpOk = true;
                }
            }, 1000);
        }


        // 우편번호 주소검색
        // 다음 우편번호 API 스크립트 상단 추가, postcode 함수 utils.js 파일 추가
        const btnZip = document.getElementById('btnZip');
        btnZip.onclick = function (){
            postcode();
        }

        // 최종 유효성 검사 확인
        document.registerForm.onsubmit = function (){

            if(!isUidOk){
                alert('아이디가 유효하지 않습니다.');
                return false;
            }

            if(!isPassOk){
                alert('비밀번호가 유효하지 않습니다.');
                return false;
            }

            if(!isNameOk){
                alert('이름이 유효하지 않습니다.');
                return false;
            }

            if(!isNickOk){
                alert('별명이 유효하지 않습니다.');
                return false;
            }

            if(!isEmailOk){
                alert('이메일이 유효하지 않습니다.');
                return false;
            }

            if(!isHpOk){
                alert('휴대폰이 유효하지 않습니다.');
                return false;
            }

            // 폼 전송
            return true;
        }
    }
