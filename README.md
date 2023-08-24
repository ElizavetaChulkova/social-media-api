## Social Media API

##### Цель: разработать REST API для социальной медиа платформы, позволяющей пользователям регистрироваться, входить в систему, создавать посты, переписываться, подписываться на других пользователейси получать свою ленту активности.

Требования:

##### 1. Аутентификация и авторизация:

- Пользователи могут зарегестрироваться, указав имя пользователя, электронную почту и пароль.
- Пользователи могут войти в систему, предоставив правильные учётные данные.
- API должен обеспечивать защиту конфиденциальности пользовательских данных, включая хэширование паролей и использование JWT.

##### 2. Управление постами:

- Пользователи могут создавать новые посты, указывая текст, заголовок и прикрепляя изображения.
- Пользователи могут просматривать посты других пользователей.
- Пользователи могут обновлять и удалять свои собственные посты.

##### 3. Взаимодействие пользователей:

- Пользователи могут отправлять заявки в друзья другим пользователям. С этого момента, пользователь, отправивший заявку, остается подписчиком до тех пор, пока сам не откажется от подписки. Если пользователь, получивший заявку, принимает ее, оба пользователя становятся друзьями. Если отклонит, то пользователь, отправивший заявку, как и указано ранее, все равно остается подписчиком.
- Пользователи, являющиеся друзьями, также являются подписчиками друг на друга.

##### 4. Обработка ошибок:
- API должно обрабатывать и возвращать понятные сообщения об ошибках при неправильном запросе или внутренних проблемах сервера.
- API должно осуществлять валидацию введенных данных и возвращать информативные сообщения при неправильном формате.

##### 5. Документация API:
- API должно быть хорошо задукоментировано с использованием инструментов, таких как Swagger или OpenAPI.
- Документация должна содержать описания доступных эндпоинтов, форматы запросов и ответов, а также требования к аутентификации.

### Swagger documentation

<a href="http://localhost:8080/swagger-ui/index.html#/">Social media API OpenAPI specification</a>
