use axum::{http::StatusCode, response::IntoResponse, Json};
use serde::{Deserialize, Serialize};

pub async fn process_data(Json(request): Json<DataRequest>) -> impl IntoResponse {
    // Calculate sums and return response
    let mut string_len: usize = 0;
    let mut int_sum: i32 = 0;
    for v in request.data {
        match v {
            DataVal::Str(x) => string_len += x.len(),
            DataVal::Int(y) => int_sum += y,
        }
    }

    let response = DataResponse {
        string_len,
        int_sum,
    };

    (StatusCode::OK, Json(response))
}

#[derive(Debug, Deserialize)]
#[serde(untagged)]
enum DataVal {
    Str(String),
    Int(i32),
}

#[derive(Deserialize)]
pub struct DataRequest {
    data: Vec<DataVal>,
}

#[derive(Serialize)]
pub struct DataResponse {
    string_len: usize,
    int_sum: i32,
}
