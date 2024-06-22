<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class ResponseResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */

    public $status;
    public $message;
    public $result;

    /**
    *__construct
    
    *@param boolean $status
    *@param string $message
    *@param mixed $result
    *@return void
    */
    public function __construct($status, $message, $result)
    {
        parent::__construct($result);
        $this->status = $status;
        $this->message = $message;
    }

    /**
     * toArray
     * 
     * @param mixed $request
     * @return array
     */
    public function toArray(Request $request): array
    {
        return [
            'status' => $this->status,
            'message' => $this->message,
            'data' => $this->result
        ];
    }
}
