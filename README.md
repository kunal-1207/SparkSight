# SparkSight Runbook

This guide contains everything you need to set up, run, and deploy the SparkSight platform locally and in the cloud. It also details the required sensitive configuration files and credentials needed to connect the components.

## Prerequisites

- **Java 11 or 17**: Required for Spark 3.5.
- **Scala 2.13 & sbt (1.9+)**: For the backend.
- **Node.js 18+ & npm**: For the Vite/React frontend.
- **Terraform 1.5+**: For infrastructure deployment.
- **Google Cloud SDK (`gcloud`)**: For GCP authentication.

---

## 1. Sensitive Files & Credentials Setup

To run SparkSight, you need specific credentials to interact with Google Cloud Storage and BigQuery.

### GCP Service Account Key (`credentials.json`)
You must create a Service Account in GCP with the following roles:
- `BigQuery Data Editor`
- `Storage Object Viewer`

1. Go to GCP Console -> IAM & Admin -> Service Accounts.
2. Create a new service account and assign the roles above.
3. Generate a new JSON key and download it.
4. Save the file exactly as `backend/src/main/resources/credentials.json`. 

*(Do not commit this file to version control. It should be added to `.gitignore`)*

**Mock `credentials.json` Example (For Format Reference):**
```json
{
  "type": "service_account",
  "project_id": "your-gcp-project-id",
  "private_key_id": "abc123def456...",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIE...\n-----END PRIVATE KEY-----\n",
  "client_email": "sparksight-sa@your-gcp-project-id.iam.gserviceaccount.com",
  "client_id": "1029384756",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token"
}
```

### Backend Configuration (`backend/.env`)
Create a `backend/.env` file to configure application variables and tell the backend where to find the GCP credentials:
```env
GOOGLE_APPLICATION_CREDENTIALS=src/main/resources/credentials.json
PROJECT_ID=your-gcp-project-id
DATASET_ID=sparksight
PORT=8080
```

### Frontend Configuration (`frontend/.env`)
Create a `frontend/.env` file to point the frontend to the correct API endpoint.
```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

---

## 2. Running the Backend

The backend is an Akka HTTP server that ingests Spark logs and serves the API.

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Compile and run the server:
   ```bash
   sbt run
   ```
   *The server will start on `http://localhost:8080`.*

---

## 3. Running the Frontend

The frontend is a Vite + React + TypeScript dashboard.

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
   *The dashboard will be available at `http://localhost:5173`.*

---

## 4. Deploying Infrastructure

Terraform is used to provision the production environment on GCP.

1. Navigate to the infrastructure directory:
   ```bash
   cd infrastructure
   ```
2. Initialize Terraform:
   ```bash
   terraform init
   ```
3. Plan the deployment. You will be prompted to provide your `project_id` and `bucket_name`:
   ```bash
   terraform plan -var="project_id=your-gcp-project-id" -var="bucket_name=sparksight-logs-bucket"
   ```
4. Apply the configuration to provision GCS, BigQuery, and Cloud Run:
   ```bash
   terraform apply
   ```

---

## 5. Testing the Pipeline

To verify the local installation:
1. Ensure both backend and frontend are running locally.
2. Send a POST request to upload a sample log:
   ```bash
   curl -X POST http://localhost:8080/api/v1/logs \
   -H "Content-Type: application/json" \
   -d '{"filePath": "datasets/sample_event_log.json"}'
   ```
3. Open the frontend dashboard (`http://localhost:5173`) and navigate to the "Application Details" view to see the extracted jobs, stages, and tasks.
