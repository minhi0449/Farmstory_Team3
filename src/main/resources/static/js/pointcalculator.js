// 가격과 포인트 비율에 따라 포인트를 자동으로 계산해주는 함수
    function calculatePoint() {
    const priceInput = document.querySelector('input[name="price"]');
    const pointSelect = document.querySelector('select[name="pointRate"]');
    const pointDisplay = document.getElementById('pointDisplay'); // 포인트를 화면에 표시할 요소
    const calculatedPointInput = document.getElementById('calculatedPoint'); // 숨겨진 input 요소

    // 가격 또는 포인트 비율이 변경될 때마다 계산
    function updatePoint() {
    const price = parseFloat(priceInput.value) || 0;  // 가격이 숫자인지 확인
    const pointRate = parseFloat(pointSelect.value) || 0; // 선택된 퍼센티지 값
    const point = Math.floor(price * (pointRate / 100));  // 가격의 선택된 퍼센트 계산

    // 화면에 포인트 표시
    pointDisplay.textContent = point + ' 포인트';

    // 숨겨진 input에 계산된 포인트 값 저장
    calculatedPointInput.value = point;
}

    // 가격 입력 및 포인트 비율 변경 시 이벤트 발생
    priceInput.addEventListener('input', updatePoint);
    pointSelect.addEventListener('change', updatePoint);


}
    function updateFileName(input, fileNameId) {
    const fileNameDisplay = document.getElementById(fileNameId);
    if (input.files.length > 0) {
    fileNameDisplay.textContent = input.files[0].name; // 선택된 파일 이름 업데이트
} else {
    fileNameDisplay.textContent = 'No file chosen'; // 파일이 선택되지 않은 경우
}
}
    // 페이지가 로드되면 함수 실행
    window.onload = calculatePoint;
