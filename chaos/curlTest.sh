curl -H 'Content-Type: application/json' \
	-d '{ "data": ["Hello", 1, 5, "World", "!"] }' \
	-X POST \
	http://127.0.0.1:3000/process_data
